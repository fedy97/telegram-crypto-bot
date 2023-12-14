package org.bot.operations;

import com.kucoin.sdk.KucoinClientBuilder;
import com.kucoin.sdk.KucoinRestClient;
import com.kucoin.sdk.exception.KucoinApiException;
import com.kucoin.sdk.rest.request.WithdrawApplyRequest;
import com.kucoin.sdk.rest.response.ApiCurrencyDetailChainPropertyResponseV2;
import lombok.extern.slf4j.Slf4j;
import org.bot.utils.EnvVars;
import org.bot.utils.exceptions.CommandExecutionException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class KucoinOperations implements Operations {

    private final String apiKey = EnvVars.getEnvVar("KUCOIN_API_KEY");
    private final String passphrase = EnvVars.getEnvVar("KUCOIN_PASSPHRASE");
    private final String secretKey = EnvVars.getEnvVar("KUCOIN_SECRET_KEY");
    private KucoinRestClient kucoinRestClient;
    private final String baseUrl;

    public KucoinOperations() {
        // get from envs the api keys
        this.baseUrl = Boolean.parseBoolean(EnvVars.getEnvVar("TEST")) ? "https://openapi-sandbox.kucoin.com" : "https://openapi-v2.kucoin.com";
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
                throw new CommandExecutionException(e.getMessage() + " You input " + chain + ". Valid chain ids are: " + getAvailableChains(ticker));
            }
            throw new CommandExecutionException(e.getMessage().replace('.', ' '));
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
    public Set<String> getAvailableChains(String ticker) {
        try {
            List<ApiCurrencyDetailChainPropertyResponseV2> chains = kucoinRestClient.currencyAPI().getCurrencyDetailV3(ticker.toUpperCase(), null).getChains();
            return chains.stream().map((ApiCurrencyDetailChainPropertyResponseV2::getChainId)).collect(Collectors.toSet());
        } catch (IOException e) {
            log.error(e.getMessage());
            return new HashSet<>();
        }
    }
}
