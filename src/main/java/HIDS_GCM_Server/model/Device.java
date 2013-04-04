package HIDS_GCM_Server.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Device database table.
 * 
 */
@Entity
public class Device implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String deviceSerial;

	private String codeName;

	private String deviceLabel;

	private String deviceModel;

	private String deviceOwner;

	private String gcmRegistrationId;

	private String hardware;

	private String industrialDesign;

	private String manufacturer;

	@Temporal(TemporalType.TIMESTAMP)
	private Date registerDate;

	private int totalCapacity;

	private int usedCapacity;

	//bi-directional many-to-one association to DeviceApplication
	@OneToMany(mappedBy="device1", fetch = FetchType.EAGER, cascade={CascadeType.ALL})
	private List<DeviceApplication> runningApplications;

	//bi-directional many-to-one association to DeviceApplication
	@OneToMany(mappedBy="device2", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
	private List<DeviceApplication> installedApplications;

	//bi-directional many-to-one association to VirusStatus
	@ManyToOne
	@JoinColumn(name="VirusStatus")
	private VirusStatus virusStatusBean;

	public Device() {
	}

	public String getDeviceSerial() {
		return this.deviceSerial;
	}

	public void setDeviceSerial(String deviceSerial) {
		this.deviceSerial = deviceSerial;
	}

	public String getCodeName() {
		return this.codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getDeviceLabel() {
		return this.deviceLabel;
	}

	public void setDeviceLabel(String deviceLabel) {
		this.deviceLabel = deviceLabel;
	}

	public String getDeviceModel() {
		return this.deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getDeviceOwner() {
		return this.deviceOwner;
	}

	public void setDeviceOwner(String deviceOwner) {
		this.deviceOwner = deviceOwner;
	}

	public String getGcmRegistrationId() {
		return this.gcmRegistrationId;
	}

	public void setGcmRegistrationId(String gcmRegistrationId) {
		this.gcmRegistrationId = gcmRegistrationId;
	}

	public String getHardware() {
		return this.hardware;
	}

	public void setHardware(String hardware) {
		this.hardware = hardware;
	}

	public String getIndustrialDesign() {
		return this.industrialDesign;
	}

	public void setIndustrialDesign(String industrialDesign) {
		this.industrialDesign = industrialDesign;
	}

	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Date getRegisterDate() {
		return this.registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public int getTotalCapacity() {
		return this.totalCapacity;
	}

	public void setTotalCapacity(int totalCapacity) {
		this.totalCapacity = totalCapacity;
	}

	public int getUsedCapacity() {
		return this.usedCapacity;
	}

	public void setUsedCapacity(int usedCapacity) {
		this.usedCapacity = usedCapacity;
	}

	public List<DeviceApplication> getRunningApplications() {
		return this.runningApplications;
	}

	public void setRunningApplications(List<DeviceApplication> runningApplications) {
		this.runningApplications = runningApplications;
	}

	public List<DeviceApplication> getInstalledApplications() {
		return this.installedApplications;
	}

	public void setInstalledApplications(List<DeviceApplication> installedApplications) {
		this.installedApplications = installedApplications;
	}

	public VirusStatus getVirusStatusBean() {
		return this.virusStatusBean;
	}

	public void setVirusStatusBean(VirusStatus virusStatusBean) {
		this.virusStatusBean = virusStatusBean;
	}

}