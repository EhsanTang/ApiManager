package cn.crap.model;

import java.util.Date;

public class BugPO extends BasePo{
	private String assignedTo;
	private Byte priority;
	private String updateBy;
	private String name;
	private String content;
	private Byte severity;
	private String creator;
	private String moduleId;
	private String attributes;
	private String tester;
	private String projectId;
	private Byte type;
	private String tracer;
	private Byte status;
	private Date deadline;

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public Byte getPriority() {
		return priority;
	}

	public void setPriority(Byte priority) {
		this.priority = priority;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Byte getSeverity() {
		return severity;
	}

	public void setSeverity(Byte severity) {
		this.severity = severity;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	@Override
	public String getAttributes() {
		return attributes;
	}

	@Override
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public String getTester() {
		return tester;
	}

	public void setTester(String tester) {
		this.tester = tester;
	}

	@Override
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Byte getTrace() {
		return type;
	}

	public void setTrace(Byte type) {
		this.type = type;
	}

	public String getTracer() {
		return tracer;
	}

	public void setTracer(String tracer) {
		this.tracer = tracer;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
}
