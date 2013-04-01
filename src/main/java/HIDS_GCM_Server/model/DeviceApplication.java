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

    private String versionNumber;

    private String processName;

    private boolean scanning;

	@Column(name="package")
	private String appPackage;

	//bi-directional many-to-one association to Device
	@ManyToOne
	@JoinColumn(name="deviceSerialRunning")
	private Device device1;

	//bi-directional many-to-one association to Device
	@ManyToOne
	@JoinColumn(name="deviceSerialInstalled")
	private Device device2;

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

	public void setAppPackage(String appPackage) {
		this.appPackage = appPackage;
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

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public boolean isScanning() {
        return scanning;
    }

    public void setScanning(boolean scanning) {
        this.scanning = scanning;
    }
}