package cn.ddcollection.model;

/**
 * @author 作者 E-mail:
 * @date 创建时间：2016年3月15日 下午6:50:17
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class DataCollector {
	private int id;// 自增ID
	private String name;// 爬虫名
	private String datasources;// 数据源网站
	private String description;// 描述信息
	private String filename;// 文件名
	private int s3id;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public DataCollector() {
	}

	public DataCollector(int id, String name, String datasources, String description,
			String filename,int s3id) {
		super();
		this.id = id;
		this.name = name;
		this.datasources = datasources;
		this.description = description;
		this.filename = filename;
		this.s3id = s3id;
	}

	public DataCollector(String name, String datasources, String description,String filename,int s3id) {
		super();
		this.name = name;
		this.datasources = datasources;
		this.description = description;
		this.filename = filename;
		this.s3id = s3id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDatasources() {
		return datasources;
	}

	public void setDatasources(String datasources) {
		this.datasources = datasources;
	}

	public int getS3id() {
		return s3id;
	}

	public void setS3id(int s3id) {
		this.s3id = s3id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString(){
		return "[\"id\":"+id+",\"name\":"+name+",\"datasources\":"+datasources+",\"s3id\":"+s3id
				+",\"description\":"+description+",\"filename:\""+filename+"]";
	}
}
