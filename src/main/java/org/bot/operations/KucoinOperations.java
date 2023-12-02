package org.bot.operations;

import com.kucoin.sdk.KucoinClientBuilder;
import com.kucoin.sdk.KucoinRestClient;
import com.kucoin.sdk.rest.response.WithdrawQuotaResponse;
import lombok.extern.slf4j.Slf4j;
import org.bot.utils.EnvVars;

import java.io.IOException;

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
    public void withdraw() {
        try {
            WithdrawQuotaResponse response = kucoinRestClient.withdrawalAPI().getWithdrawQuotas("BTC", null);
            log.info(response.toString());
        } catch (IOException ioException) {
            log.error(ioException.getMessage());
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
}
