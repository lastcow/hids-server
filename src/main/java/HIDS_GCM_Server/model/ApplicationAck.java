package HIDS_GCM_Server.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ApplicationAck database table.
 * 
 */
@NamedQueries(
        @NamedQuery(
                name = "getAppAckBySignature",
                query = "SELECT appAck FROM ApplicationAck appAck where appAck.applicationSignature = :signature"
        )
)

@Entity
public class ApplicationAck implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String applicationName;

	private String applicationPackageName;

	private String applicationProcessName;

	private String applicationSignature;

	//bi-directional many-to-one association to VirusStatus
	@ManyToOne
	@JoinColumn(name="VirusStatus")
	private VirusStatus virusStatusBean;

	public ApplicationAck() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApplicationName() {
		return this.applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getApplicationPackageName() {
		return this.applicationPackageName;
	}

	public void setApplicationPackageName(String applicationPackageName) {
		this.applicationPackageName = applicationPackageName;
	}

	public String getApplicationProcessName() {
		return this.applicationProcessName;
	}

	public void setApplicationProcessName(String applicationProcessName) {
		this.applicationProcessName = applicationProcessName;
	}

	public String getApplicationSignature() {
		return this.applicationSignature;
	}

	public void setApplicationSignature(String applicationSignature) {
		this.applicationSignature = applicationSignature;
	}

	public VirusStatus getVirusStatusBean() {
		return this.virusStatusBean;
	}

	public void setVirusStatusBean(VirusStatus virusStatusBean) {
		this.virusStatusBean = virusStatusBean;
	}

}