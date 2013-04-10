package HIDS_GCM_Server.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DeviceApplication database table.
 * 
 */
@Entity
public class DeviceApplication implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String name;

	@Column(name="package")
	private String appPackage;

	private String processName;

	private boolean scanning;

	private String signature;

	private String versionName;

	private String versionNumber;

	//bi-directional many-to-one association to Device
	@ManyToOne
	@JoinColumn(name="deviceSerialRunning")
	private Device device1;

	//bi-directional many-to-one association to Device
	@ManyToOne
	@JoinColumn(name="deviceSerialInstalled")
	private Device device2;

	//bi-directional many-to-one association to VirusStatus
	@ManyToOne
	@JoinColumn(name="VirusStatus")
	private VirusStatus virusStatusBean;

	public DeviceApplication() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppPackage() {
		return this.appPackage;
	}

	public void setAppPackage(String package_) {
		this.appPackage = package_;
	}

	public String getProcessName() {
		return this.processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public boolean getScanning() {
		return this.scanning;
	}

	public void setScanning(boolean scanning) {
		this.scanning = scanning;
	}

	public String getSignature() {
		return this.signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getVersionName() {
		return this.versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getVersionNumber() {
		return this.versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public Device getDevice1() {
		return this.device1;
	}

	public void setDevice1(Device device1) {
		this.device1 = device1;
	}

	public Device getDevice2() {
		return this.device2;
	}

	public void setDevice2(Device device2) {
		this.device2 = device2;
	}

	public VirusStatus getVirusStatusBean() {
		return this.virusStatusBean;
	}

	public void setVirusStatusBean(VirusStatus virusStatusBean) {
		this.virusStatusBean = virusStatusBean;
	}

}