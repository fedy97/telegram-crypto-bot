package org.bot.operations;

import lombok.extern.slf4j.Slf4j;
import org.bot.utils.exceptions.PlatformNotAvailableException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
public class OperationsDispatcher {
    private static OperationsDispatcher instance;

    private final Map<String, Operations> operationsMap;

    private OperationsDispatcher() {
        operationsMap = new HashMap<>();
    }

    public static OperationsDispatcher getInstance() {
        if (instance == null) {
            instance = new OperationsDispatcher();
        }
        return instance;
    }

    public void register(Operations operations) {
        if (operations.isUsable()) {
            operations.build();
            operationsMap.put(operations.platform(), operations);
            log.info("Successfully registered " + operations.platform());
            return;
        }
        log.info("Missing keys for platform " + operations.platform() + ". Not registered.");
    }

    public Operations getOperations(String platform) {
        Operations operations = operationsMap.get(platform);
        if (operations != null)
            return operations;
        throw new PlatformNotAvailableException();
    }

    public Set<String> getAvailablePlatforms() {
        return this.operationsMap.keySet();
    }
}
