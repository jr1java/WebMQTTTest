import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

public class TestClient implements MqttCallbackExtended {
	
	private MqttClient iotClient;
	
	public static void main(String[] args) {
		TestClient c = new TestClient();
		c.openIotClient();
		c.pause();
		c.disconnectClient(); //crash happens here
	}
	
	private void openIotClient() {
		try {
			setupIotClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setupIotClient() throws MqttException {
		String host = "ws://localhost:15675/ws";
        String clientId = "MQTT-Test-Client";
        String tmpDir = System.getProperty("java.io.tmpdir");
    	MqttDefaultFilePersistence dataStore = new MqttDefaultFilePersistence(tmpDir);
        MqttConnectOptions conOpt = new MqttConnectOptions();
        conOpt.setUserName("1");
        conOpt.setPassword("1".toCharArray());
        conOpt.setKeepAliveInterval(30);
        conOpt.setAutomaticReconnect(true);
        conOpt.setWill("lwttest", "Client died".getBytes(), 0, false);
        TestClient c = new TestClient();
        iotClient = new MqttClient(host, clientId, dataStore);
        iotClient.setCallback(this);
        iotClient.connect(conOpt);
	}
	
	private void disconnectClient() {
		try {
			iotClient.disconnect(); //crash happens here
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}
	
	private void pause() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
	
	@Override
	public void connectionLost(Throwable cause) {
		//
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		//
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		//		
	}

	@Override
	public void connectComplete(boolean reconnect, String serverURI) {
		//		
	}
	
}
