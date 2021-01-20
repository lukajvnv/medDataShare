package rs.ac.uns.ftn.medDataShare.chaincode.util;

import org.hyperledger.fabric.gateway.Identity;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.hyperledger.fabric.gateway.X509Identity;
import rs.ac.uns.ftn.medDataShare.chaincode.Config;

import java.io.IOException;
import java.nio.file.Paths;

public class WalletUtil {

    private Wallet wallet;

    public Wallet getWallet() {
        return wallet;
    }

    public WalletUtil() {
        try {
//            wallet = Wallets.newInMemoryWallet();
            wallet = Wallets.newFileSystemWallet(Paths.get(Config.WALLET_DIRECTORY));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public X509Identity getIdentity(String userIdentityId) throws IOException {
        return (X509Identity) wallet.get(userIdentityId);
    }

    public void putIdentity(String userIdentityId, Identity identity) throws IOException {
        wallet.put(userIdentityId, identity);
    }




}
