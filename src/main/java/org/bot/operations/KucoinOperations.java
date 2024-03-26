package org.bot.operations;

import com.kucoin.sdk.KucoinClientBuilder;
import com.kucoin.sdk.KucoinRestClient;
import com.kucoin.sdk.exception.KucoinApiException;
import com.kucoin.sdk.rest.request.OrderCreateApiRequest;
import com.kucoin.sdk.rest.request.WithdrawApplyRequest;
import com.kucoin.sdk.rest.response.*;
import lombok.extern.slf4j.Slf4j;
import org.bot.utils.EnvVars;
import org.bot.utils.Helpers;
import org.bot.utils.exceptions.CommandExecutionException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class KucoinOperations implements Operations {

    private final String apiKey = EnvVars.getEnvVar("KUCOIN_API_KEY", null);
    private final String passphrase = EnvVars.getEnvVar("KUCOIN_PASSPHRASE", null);
    private final String secretKey = EnvVars.getEnvVar("KUCOIN_SECRET_KEY", null);
    private KucoinRestClient kucoinRestClient;
    private final String baseUrl;

    public KucoinOperations() {
        // get from envs the api keys
        this.baseUrl = Boolean.parseBoolean(EnvVars.getEnvVar("TEST", "false")) ? "https://openapi-sandbox.kucoin.com" : "https://openapi-v2.kucoin.com";
    }

    @Override
    public void withdraw(String ticker, Double amount, String chain, String address) {
        try {
            WithdrawApplyRequest withdrawApplyRequest = WithdrawApplyRequest.builder()
                    .address(address)
                    .amount(BigDecimal.valueOf(amount))
                    .currency(ticker.toUpperCase())
                    .chain(chain.toLowerCase())
                    .build();
            kucoinRestClient.withdrawalAPI().applyWithdraw(withdrawApplyRequest);
        } catch (IOException e) {
            throw new CommandExecutionException(e.getMessage());
        } catch (KucoinApiException e) {
            if (e.getCode().equals("900014")) {
                throw new CommandExecutionException(e.getMessage() + " You input " + chain + ". Valid chain ids are: " + getAvailableChains(ticker).keySet());
            }
            throw new CommandExecutionException(e.getMessage().replace('.', ' '));
        }
    }

    @Override
    public Map<String, String> deposit(String ticker, String chain) {
        try {
            if (chain != null) {
                createDepositAddress(ticker, chain);
            }
            List<DepositAddressResponse> addressResponses = kucoinRestClient.depositAPI().getDepositAddresses(ticker.toUpperCase());
            return addressResponses.stream()
                    .collect(Collectors.toMap(DepositAddressResponse::getChain, DepositAddressResponse::getAddress));
        } catch (KucoinApiException ke) {
            if (ke.getCode().equals("900014")) {
                throw new CommandExecutionException(ke.getMessage() + " You input " + chain + ". Valid chain ids are: " + getAvailableChains(ticker).keySet());
            }
            throw new CommandExecutionException(ke.getMessage());
        } catch (Exception e) {
            throw new CommandExecutionException(e.getMessage());
        }
    }

    private void createDepositAddress(String ticker, String chain) {
        try {
            kucoinRestClient.depositAPI().createDepositAddress(ticker.toUpperCase(), chain.toUpperCase());
        } catch (KucoinApiException | IOException e) {
            // continue if address already exist
            log.warn(e.getMessage());
        }
    }

    @Override
    public boolean isUsable() {
        return this.passphrase != null && this.apiKey != null && this.secretKey != null;
    }

    @Override
    public void build() {
        KucoinClientBuilder builder = new KucoinClientBuilder().withBaseUrl(this.baseUrl).withApiKey(this.apiKey, this.secretKey, this.passphrase);
        this.kucoinRestClient = builder.buildRestClient();
    }

    @Override
    public String platform() {
        return "kucoin";
    }

    @Override
    public Map<String, Double> getAvailableChains(String ticker) {
        List<ApiCurrencyDetailChainPropertyResponseV2> chains;
        try {
            chains = kucoinRestClient.currencyAPI().getCurrencyDetailV3(ticker.toUpperCase(), null).getChains();
        } catch (IOException | KucoinApiException e) {
            log.error(e.getMessage());
            throw new CommandExecutionException(e.getMessage());
        }
        List<WithdrawQuotaResponse> responses = chains.stream().map(chain -> {
            try {
                return kucoinRestClient.withdrawalAPI().getWithdrawQuotas(ticker.toUpperCase(), chain.getChainId());
            } catch (IOException | KucoinApiException e) {
                log.error(e.getMessage());
                throw new CommandExecutionException(e.getMessage());
            }
        }).collect(Collectors.toList());
        Map<String, Double> map = new HashMap<>();
        IntStream.range(0, chains.size())
                .forEach(i -> map.put(chains.get(i).getChainId(), responses.get(i).getWithdrawMinFee().doubleValue()));
        return map;
    }

    @Override
    public Map<String, Double> getBalance() {
        Map<String, Double> balance = new HashMap<>();
        try {
            List<AccountBalancesResponse> accounts = kucoinRestClient.accountAPI().listAccounts(null, null);
            accounts.stream()
                    .filter(account -> account.getBalance().doubleValue() > 0)
                    .forEach(account -> Helpers.addToMapOrSum(balance, account.getCurrency(), account.getBalance().doubleValue()));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new CommandExecutionException(e.getMessage());
        } catch (KucoinApiException e) {
            throw new CommandExecutionException(e.getMessage().replace('.', ' '));
        }
        return balance;
    }

    @Override
    public String trade(String action, String ticker, String type, Double amount, Double price) {
        try {
            OrderCreateApiRequest request = OrderCreateApiRequest.builder()
                    .side(action)
                    .type(type)
                    .price(price != null ? BigDecimal.valueOf(price) : null)
                    .size(action.equals("buy") ? null : BigDecimal.valueOf(amount))
                    .funds(action.equals("buy") ? BigDecimal.valueOf(amount) : null)
                    .symbol(ticker + "-USDT")
                    .clientOid(UUID.randomUUID().toString())
                    .build();

            OrderCreateResponse response = kucoinRestClient.orderAPI().createOrder(request);
            return response.getOrderId();
        } catch (IOException | KucoinApiException e) {
            throw new CommandExecutionException(e.getMessage());
        }
    }
}
