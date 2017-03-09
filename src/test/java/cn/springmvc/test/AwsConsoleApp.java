package cn.springmvc.test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.AvailabilityZone;
import com.amazonaws.services.ec2.model.CreateKeyPairRequest;
import com.amazonaws.services.ec2.model.CreateKeyPairResult;
import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesResult;
import com.amazonaws.services.ec2.model.DescribeImagesResult;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Image;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.KeyPair;
import com.amazonaws.services.ec2.model.Placement;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesResult;

/**
 * @author 作者 E-mail:
 * @date 创建时间：2016年3月14日 下午9:22:51
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class AwsConsoleApp {
	// Important: Be sure to fill in your AWS access credentials in the
	// AwsCredentials.properties file before you try to run this
	// sample.
	// http://aws.amazon.com/security-credentials

	// this is the proxy for all the EC2 operations and the starting class for
	// reading the javadoc.
	// If you want to have a feeling of what you can do with EC2 well.. look at
	// this object!
	// http://docs.amazonwebservices.com/AWSJavaSDK/latest/javadoc/com/amazonaws/services/ec2/AmazonEC2.html
	static AmazonEC2 ec2;

	/**
	 * The only information needed to create a client are security credentials
	 * consisting of the AWS Access Key ID and Secret Access Key. All other
	 * configuration, such as the service endpoints, are performed
	 * automatically. Client parameters, such as proxies, can be specified in an
	 * optional ClientConfiguration object when constructing a client.
	 *
	 * @see com.amazonaws.auth.BasicAWSCredentials
	 * @see com.amazonaws.auth.PropertiesCredentials
	 * @see com.amazonaws.ClientConfiguration
	 */
	private static void init() throws Exception {
		AWSCredentials credentials = new PropertiesCredentials(
				AwsConsoleApp.class.getResourceAsStream("AwsCredentials.properties"));

		ec2 = new AmazonEC2Client(credentials);

	}

	/**
	 * Creates a single Instance of a given image. This is just a small wrap of
	 * the createAMInstances method. AMI= Amazon Machine Image and represents an
	 * image of a machine that has been uploaded in amazon and in not referring
	 * to a running machine.
	 * 
	 * @param amiId
	 *            unique ID of an Amazon Machine Image (AMI)
	 * @param keyPairName
	 *            keyPair that will be use for executing the operation.
	 * @return unique ID of the running Instance of the give AMI
	 * @throws AmazonServiceException
	 *             something wrong in Amazon
	 * @throws Exception
	 *             some communication failure
	 */
	public static String createAMInstanceSmall(String amiId, String keyPairName)
			throws AmazonServiceException, Exception {
		String runninginstanceID = "";
		// see this method for more details
		List<String> runninginstanceIDs = createAMInstances(amiId, 1, 1, keyPairName, "m1.small", "us-east-1a");

		// do not program in this way... the list should be controlled
		runninginstanceID = runninginstanceIDs.iterator().next();

		return runninginstanceID;
	}

	/**
	 * Create a key pair for a give user. The Keys are stored in yourHome/.ec2
	 * or in the place that you specify while configuring the AWS plug-in for
	 * java development. Note that you can always refer to this key pair using
	 * just the name as long as the keys are correctly store in you machine.
	 * 
	 * @param keyPairName
	 *            unique name of the key pair
	 * @return Object representing the Key pair
	 * @throws AmazonServiceException
	 *             something wrong in Amazon
	 * @throws Exception
	 *             some communication failure
	 */
	public static KeyPair createKeyPair(String keyPairName) throws AmazonServiceException, Exception {
		// Create key pair for the given user.
		// request.getSecurityGroups().add("groupname");//create security groups
		// if you want create it! the security group will be default!
		// make sure to have access to SSH port 22 on the default group on the
		// EC2console
		CreateKeyPairRequest kpReq = new CreateKeyPairRequest();
		kpReq.setKeyName(keyPairName);
		CreateKeyPairResult kpres = ec2.createKeyPair(kpReq);
		KeyPair keyPair = kpres.getKeyPair();
		System.out.println("You havekeyPair.getKeyName = " + keyPair.getKeyName() + "\nkeyPair.getKeyFingerprint()="
				+ keyPair.getKeyFingerprint() + "\nkeyPair.getKeyMaterial()=" + keyPair.getKeyMaterial());
		return keyPair;
	}

	/**
	 * Create one or more running instances of a given AMI. If you want to lunch
	 * a machine in the amazon cloud this is the method that you are looking
	 * for. The key method is RunInstancesResult runInstancesRes =
	 * ec2.runInstances(request); that return a unique id of the reservation for
	 * retrieving the running instances. By the way if you do that you will have
	 * to query the amazon APIs, it will be slower and will cost you but is good
	 * to know that this information will be always there.
	 * 
	 * @param AMId
	 *            unique ID of the machine
	 * @param min
	 *            minimum number of machines
	 * @param max
	 *            maximum number of machines that you want to run
	 * @param keyPairName
	 *            unique id of the keypair that will be used for running an
	 *            instance
	 * @param insType
	 *            type of instance (i.e. m1.small)
	 * @param availabilityZone
	 *            where the instance should run
	 * @return List of unique ID of running instances
	 * @throws AmazonServiceException
	 *             something wrong in Amazon
	 * @throws Exception
	 *             some communication failure
	 */
	public static List<String> createAMInstances(String AMId, int min, int max, String keyPairName, String insType,
			String availabilityZone) throws AmazonServiceException, Exception {
		List<String> runninginstanceIDs = new ArrayList<String>();
		RunInstancesRequest request = new RunInstancesRequest();

		// Chose the type i.e. m1.small
		request.setInstanceType(insType);

		// Minimum number of instance that you intend to create
		request.setMinCount(min);
		// Maximum number
		request.setMaxCount(max);

		// Chose the zone
		Placement p = new Placement();
		p.setAvailabilityZone(availabilityZone);
		request.setPlacement(p);
		request.setImageId(AMId);

		request.setKeyName(keyPairName);// assign Keypair name for this request

		RunInstancesResult runInstancesRes = ec2.runInstances(request);
		String reservationId = runInstancesRes.getReservation().getReservationId();

		// Getting the list of running instances according with our request
		Reservation reservation = runInstancesRes.getReservation();
		List<Instance> instances = reservation.getInstances();
		if (!instances.isEmpty()) {
			Iterator<Instance> instIterator = instances.iterator();
			int count = 0;
			// getting the descriptions of the instances
			while (instIterator.hasNext()) {
				Instance runningInst = instIterator.next();
				System.out.println("We just start the Instance " + count + " UniqueID: " + runningInst.getInstanceId()
						+ " ImageId " + runningInst.getImageId() + " type: " + runningInst.getInstanceType()
						+ " Started by " + runningInst.getKeyName() + " Status: " + runningInst.getState().toString());
				// Unique ID of the image that is running
				String uniqueID = runningInst.getInstanceId();
				runninginstanceIDs.add(uniqueID);
				count++;
			}
		}

		// Optionally you can use the unique id of the reservation for
		// retrieving the running instance.
		// if you do that you will have to query the amazon APIs, it will be
		// slower and will cost you
		// but is good to know that this information will be always there.
		System.out.println("reservation ID of the executed transation: " + reservationId);

		return runninginstanceIDs;

	}

	/**
	 * Retrieve the list of zone that can be used for running the instances with
	 * the user credential loaded during the init()
	 * 
	 * @return verbose string describing the zones
	 * @throws AmazonServiceException
	 *             something wrong in Amazon
	 * @throws Exception
	 *             some communication failure
	 */
	public static String listAvailableZones() throws AmazonServiceException, Exception {
		String ret = "";
		DescribeAvailabilityZonesResult availabilityZonesResult = ec2.describeAvailabilityZones();
		ret += "You have access to " + availabilityZonesResult.getAvailabilityZones().size() + " Availability Zones.\n";
		System.out.println(
				"You have access to " + availabilityZonesResult.getAvailabilityZones().size() + " Availability Zones.");

		List<AvailabilityZone> availabilityZone = availabilityZonesResult.getAvailabilityZones();
		Iterator<AvailabilityZone> iterator = availabilityZone.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			AvailabilityZone az = iterator.next();
			ret += "Zone " + i + " " + az.getRegionName() + " " + az.getState() + " " + az.getZoneName() + "\n";
			System.out.println("Zone " + i + " " + az.getRegionName() + " " + az.getState() + " " + az.getZoneName());
			i++;
		}
		return ret;

	}

	/**
	 * Terminate (kill) a given running instance. Note that the method returns
	 * when you move from "your previous state" to "terminating" and not when
	 * the machine is actually terminated. Asynchronous calls do not solve this
	 * problem as you get notified when the machine enter in "terminating"
	 * state.
	 * 
	 * @param instanceId
	 *            unique ID of the running instance.
	 * @throws AmazonServiceException
	 *             something wrong in Amazon
	 * @throws Exception
	 *             some communication failure
	 */
	public static void terminateAMI(String instanceId) throws AmazonServiceException, Exception {
		TerminateInstancesRequest rq = new TerminateInstancesRequest();

		rq.getInstanceIds().add(instanceId);
		// the method returns when you move from "your previous state" to
		// terminating and not when the machine is actually terminated.
		// You have the same problems if you use asynchronous call too
		TerminateInstancesResult rsp = ec2.terminateInstances(rq);

		System.out.println("InsanceID Terminated: " + rsp.toString());

	}

	/**
	 * Get a list of the current running instances. The key method is
	 * DescribeInstancesResult describeInstancesResult =
	 * ec2.describeInstances(); That returns information about instances that
	 * you own. Note that gives you all the reservation that are currently
	 * accounted in amazon. In other words an instance that has been stopped a
	 * few minute ago is still consider "running instance for amazon". To refine
	 * this list you need to use the method runningInst.getState().toString()
	 * that tells you if the machine is really running or is terminated or
	 * stopped or stopping or terminating or pending etc..
	 * 
	 * @return Verbose description of the running instances.
	 * @throws AmazonServiceException
	 *             something wrong in Amazon
	 * @throws Exception
	 *             some communication failure
	 */
	public static String getRunningInstances() throws AmazonServiceException, Exception {
		String ret = "";
		// The DescribeInstances operation returns information about instances
		// that you own.
		// Note that gives you all the reservation that are currently accounted
		// in amazon.
		// in other words an instance that has been stopped a few minute ago is
		// still consider "running instance for amazon".
		// the method runningInst.getState().toString() (see below) tell you if
		// the machine is really running or is terminated or stopped or stopping
		// or terminating or pending etc..
		DescribeInstancesResult describeInstancesResult = ec2.describeInstances();

		// The list of reservations containing the describes instances.
		List<Reservation> reservations = describeInstancesResult.getReservations();
		Set<Instance> instances = new HashSet<Instance>();

		for (Reservation reservation : reservations) {

			// Add the list of Amazon EC2 instances for this reservation.
			instances.addAll(reservation.getInstances());
		}
		ret += "You have " + instances.size() + " Amazon EC2 instance(s) running.\n";
		System.out.println("You have " + instances.size() + " Amazon EC2 instance(s) running.");

		if (!instances.isEmpty()) {
			Iterator<Instance> instIterator = instances.iterator();
			int count = 0;
			// getting the descriptions of our running instances
			while (instIterator.hasNext()) {
				// the method runningInst.getState().toString() tell you if the
				// machine is really running or is terminated or stopped or
				// stopping or terminating or pending etc..
				Instance runningInst = instIterator.next();
				ret += "Instance " + count + " ImageId " + runningInst.getImageId() + " type: "
						+ runningInst.getInstanceType() + " Started by " + runningInst.getKeyName() + " Status: "
						+ runningInst.getState().toString() + "\n";
				System.out.println("Instance " + count + " ImageId " + runningInst.getImageId() + " type: "
						+ runningInst.getInstanceType() + " Started by " + runningInst.getKeyName() + " Status: "
						+ runningInst.getState().toString());
				count++;
			}
		}

		return ret;

	}

	/**
	 * Retrieve the list of possible Amazon Machine Image (AMI) that can be
	 * instantiated by the user represented in the credential loaded during the
	 * init() method. All the public AMI will be retrieved so the list will be
	 * long. Many of the AMI will not have a description or a name but only an
	 * ID so they will be useless. The AMI "ami-11ca2d78" is a sort of default
	 * machine for amazon that all the user will be able to load
	 * 
	 * @return Verbose description of the running instances.
	 * @throws AmazonServiceException
	 *             something wrong in Amazon
	 * @throws Exception
	 *             some communication failure
	 */
	public static String getAvailableImages() throws AmazonServiceException, Exception {
		String ret = "";
		DescribeImagesResult describeImagesResult = ec2.describeImages();
		List<Image> listOfImages = describeImagesResult.getImages();
		Iterator<Image> listOfImagesIterator = listOfImages.iterator();
		int count = 0;
		while (listOfImagesIterator.hasNext()) {
			Image img = listOfImagesIterator.next();
			// un-comment this if you want to filter to a given id. The default
			// list of available images is long
			// if (img.getImageId().contains("11ca2d78"))
			// {
			ret += "Image " + count + " Name: " + img.getName() + " Description: " + img.getDescription() + " Id: "
					+ img.getImageId() + "\n";
			System.out.println("Image " + count + " Name: " + img.getName() + " Description: " + img.getDescription()
					+ " Id: " + img.getImageId());

			// }

			count++;
		}

		return ret;

	}

	public static void main(String[] args) throws Exception {

		// load the credential stored n AwsCredentials.properties
		init();

		/*
		 * create, delete, and administer instances .
		 *
		 * In this sample, we use an EC2 client to get a list of all the
		 * availability zones, and all instances sorted by reservation id.
		 */

		try {

			listAvailableZones();

			// getAvailableImages();

			getRunningInstances();

			Thread.currentThread().sleep(1 * 1000);

			// uncomment this method if you want to create a new kay pair name
			KeyPair keypair = createKeyPair("mySmallTest");

			// createAMInstances("ami-11ca2d78", 1, 1, "mySmallTest",
			// "m1.small", "us-east-1a");
			// the method call is terminated when the virtual machine (instance)
			// is in starting state and not in running.
			// When the machine is actually running? Well you have to poll for
			// that!
			String runningInstanceID = createAMInstanceSmall("ami-11ca2d78", "mySmallTest");

			// The creation require time or maybe the information system of
			// amazon need time to update the status.
			// In this respect the Future object does not help you. Because the
			// transaction may be successfully executed
			// but still if you look for the uniqueID of a running image you get
			// an exception.
			Thread.currentThread().sleep(60 * 1000);

			// Gives you all running instances included the one that are
			// terminated few minutes ago. (Surprice..)
			getRunningInstances();

			Thread.currentThread().sleep(1 * 1000);

			// move from whatherver previous state to "terminating" and is
			// actually terminate few seconds later (worst case 1 minute)
			// When the machine is actually stopped? Well you have to poll for
			// that!
			// Note also that the cost of a machine is counted per 30min
			// minimum. In other words there is no difference between if you
			// start and stop a machine in 3 seconds or
			// using it for 30 minutes!
			// Note that if you do "start", "stop", "start", "stop" is like
			// using 2 machine for 30 minutes!

			terminateAMI(runningInstanceID);

			Thread.currentThread().sleep(1 * 1000);

			getRunningInstances();

		} catch (AmazonServiceException ase) {
			System.out.println("Caught Exception: " + ase.getMessage());
			System.out.println("Reponse Status Code: " + ase.getStatusCode());
			System.out.println("Error Code: " + ase.getErrorCode());
			System.out.println("Request ID: " + ase.getRequestId());
		}

	}
}
