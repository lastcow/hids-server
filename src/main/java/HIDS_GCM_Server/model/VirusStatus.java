package HIDS_GCM_Server.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the VirusStatus database table.
 * 
 */
@NamedQueries(
        @NamedQuery(
           name = "getVirusStatusByStatus",
           query = "SELECT vStatus FROM VirusStatus vStatus where vStatus.status = :status"
        )
)
@Entity
public class VirusStatus implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String status;

	//bi-directional many-to-one association to ApplicationAck
	@OneToMany(mappedBy="virusStatusBean")
	private List<ApplicationAck> applicationAcks;

	//bi-directional many-to-one association to Device
	@OneToMany(mappedBy="virusStatusBean")
	private List<Device> devices;

	//bi-directional many-to-one association to DeviceApplication
	@OneToMany(mappedBy="virusStatusBean")
	private List<DeviceApplication> deviceApplications;

	public VirusStatus() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ApplicationAck> getApplicationAcks() {
		return this.applicationAcks;
	}

	public void setApplicationAcks(List<ApplicationAck> applicationAcks) {
		this.applicationAcks = applicationAcks;
	}

	public List<Device> getDevices() {
		return this.devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public List<DeviceApplication> getDeviceApplications() {
		return this.deviceApplications;
	}

	public void setDeviceApplications(List<DeviceApplication> deviceApplications) {
		this.deviceApplications = deviceApplications;
	}

}