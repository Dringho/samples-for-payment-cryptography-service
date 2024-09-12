package aws.sample.paymentcryptography;

import java.util.List;
import java.util.concurrent.ExecutionException;

import software.amazon.awssdk.services.paymentcryptography.PaymentCryptographyClient;
import software.amazon.awssdk.services.paymentcryptography.model.Key;
import software.amazon.awssdk.services.paymentcryptography.model.KeyAttributes;
import software.amazon.awssdk.services.paymentcryptography.model.KeySummary;
import software.amazon.awssdk.services.paymentcryptography.model.ListKeysRequest;


/* 
 * Usage - 
 * 
 * ./run_example.sh aws.sample.paymentcryptography.ListKeysUtil
 * 
 */
public class ListKeysUtil {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        PaymentCryptographyClient client = ControlPlaneUtils.getControlPlaneClient();
        ListKeysRequest request = ListKeysRequest.builder().maxResults(2).build();
        List<KeySummary> keys = client.listKeys(request).keys();
        if (keys.size() == 0) {
            System.out.println("No keys found");
            return;
        }

        for (KeySummary key : keys) {
            Key fullKey = ControlPlaneUtils.getKey(key.keyArn());
            KeyAttributes attrs = fullKey.keyAttributes();
            System.out.println(String.format(
                    "%s (%s / %s / %s)",
                    key.keyArn(),
                    attrs.keyClass(),
                    attrs.keyAlgorithm(),
                    attrs.keyUsage()));
        }
    }
}