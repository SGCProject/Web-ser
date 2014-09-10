package tmp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/callupload")
public class RestServiceUploadProvider {
	private static transient Logger log = LogManager.getLogger(RestServiceUploadProvider.class);

	@POST
	@Path("/uploadSingleFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String uploadSingleFile(@FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("file") FormDataContentDisposition fileDetail) {
		log.debug("Enter uploadSingleFile");
		try {
			String uploadedFileLocation = "e:/uploaded/" + fileDetail.getFileName();
			log.debug("uploadedFile = " + uploadedFileLocation);
			writeToFile(uploadedInputStream, uploadedFileLocation);
			String output = "File uploaded to : " + uploadedFileLocation;

			// writeToDB(uploadedInputStream);
			return "complete";

			// return Response.status(200).entity(output).build();
		} catch (Exception e) {
			log.error("Error uploadSingleFile", e);
		} finally {
			log.debug("Exit uploadSingleFile");
		}

		return "เกิดความผิดพลาดในการ Upload File";
	}

	private static final String FILE_UPLOAD_PATH = "E:/uploaded";
	private static final String SUCCESS_RESPONSE = "Successful";
	private static final String FAILED_RESPONSE = "Failed";

	@POST
	@Path("/uploadMultipleFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String uploadMultipleFile(@Context HttpServletRequest request) {
		System.out.println("Enter uploadMultipleFile");
		try {
			String responseStatus = SUCCESS_RESPONSE;

			if (ServletFileUpload.isMultipartContent(request)) {
				final FileItemFactory factory = new DiskFileItemFactory();
				final ServletFileUpload fileUpload = new ServletFileUpload(factory);
				try {
					
					final List items = fileUpload.parseRequest(request);

					if (items != null) {
						final Iterator iter = items.iterator();
						while (iter.hasNext()) {
							final FileItem item = (FileItem) iter.next();
							final String itemName = item.getName();
							final String fieldName = item.getFieldName();
							final String fieldValue = item.getString();

							if (item.isFormField()) {
								//กรณีส่งมาเป็น form field ให้ decode เพื่อเป็นภาษาไทย
								System.out.println("Field Name: " + fieldName + ", Field Value: " + URLDecoder.decode(fieldValue, "UTF-8"));
							} else {
								final File savedFile = new File(FILE_UPLOAD_PATH + File.separator + itemName);
								System.out.println("Saving the file: " + savedFile.getName());
								item.write(savedFile);
							}
						}
					}
				} catch (FileUploadException fue) {
					responseStatus = FAILED_RESPONSE;
					fue.printStackTrace();
				} catch (Exception e) {
					responseStatus = FAILED_RESPONSE;
					e.printStackTrace();
				}
			} else {
				String username = request.getParameter("username");
				System.out.println("username = " + username);
				String password = request.getParameter("password");
				System.out.println("password = " + password);
			}

			System.out.println("Returned Response Status: " + responseStatus);

			// writeToDB(uploadedInputStream);
			return "complete";

			// return Response.status(200).entity(output).build();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error uploadMultipleFile", e);
		} finally {
			System.out.println("Exit uploadMultipleFile");
		}

		return "เกิดความผิดพลาดในการ Upload Multiple File";
	}
	
	@POST
	@Path("/uploadMultipleFileToDB")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String uploadMultipleFileToDB(@Context HttpServletRequest request) {
		System.out.println("Enter uploadMultipleFileToDB");
		try {
			String responseStatus = SUCCESS_RESPONSE;

			if (ServletFileUpload.isMultipartContent(request)) {
				final FileItemFactory factory = new DiskFileItemFactory();
				final ServletFileUpload fileUpload = new ServletFileUpload(factory);
				try {
					
					final List items = fileUpload.parseRequest(request);

					if (items != null) {
						final Iterator iter = items.iterator();
						while (iter.hasNext()) {
							final FileItem item = (FileItem) iter.next();
							final String itemName = item.getName();
							final String fieldName = item.getFieldName();
							final String fieldValue = item.getString();

							if (item.isFormField()) {
								System.out.println("Field Name: " + fieldName + ", Field Value: " + URLDecoder.decode(fieldValue, "UTF-8"));
//								System.out.println("Field Name: " + fieldName + ", Field Value: " + fieldValue);
							} else {
								final File savedFile = new File(FILE_UPLOAD_PATH + File.separator + itemName);
								System.out.println("Saving the file: " + savedFile.getName());
								writeToDB(item.getInputStream(), item.getName());
							}
						}
					}
				} catch (FileUploadException fue) {
					responseStatus = FAILED_RESPONSE;
					fue.printStackTrace();
				} catch (Exception e) {
					responseStatus = FAILED_RESPONSE;
					e.printStackTrace();
				}
			} else {
				//กรณี ไม่ใช่ multipart
				String username = request.getParameter("username");
				System.out.println("username = " + username);
				String password = request.getParameter("password");
				System.out.println("password = " + password);
			}

			System.out.println("Returned Response Status: " + responseStatus);

			return "complete";

			// return Response.status(200).entity(output).build();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error uploadMultipleFileToDB", e);
		} finally {
			System.out.println("Exit uploadMultipleFileToDB");
		}

		return "เกิดความผิดพลาดในการ Upload Multiple File";
	}

	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
		try {
			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Error writeToFile", e);
		}

	}

	// save uploaded file to db
	private void writeToDB(InputStream uploadedInputStream, String fileName) {
		try {
			byte[] fileBytes = getBytesFromInputStream(uploadedInputStream);

			// ทดสอบเอาเข้า database
			insertTestSpsSendDetail(fileBytes, fileName);

		} catch (Exception e) {
			log.error("[LED] Error writeToDB", e);
		}
	}

	public static byte[] getBytesFromInputStream(InputStream is) throws IOException {

		int len;
		int size = 1024;
		byte[] buf;

		if (is instanceof ByteArrayInputStream) {
			size = is.available();
			buf = new byte[size];
			len = is.read(buf, 0, size);
		} else {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			buf = new byte[size];
			while ((len = is.read(buf, 0, size)) != -1)
				bos.write(buf, 0, len);
			buf = bos.toByteArray();
		}
		return buf;
	}

	private static DataSource dsCivil;

	public static Connection getConnectionCivil() throws Exception {
		try {
			if (dsCivil == null) {
				InitialContext context = new InitialContext();
				dsCivil = (DataSource) context.lookup("jdbc/db1");
			}
			return dsCivil.getConnection();
		} catch (SQLException se) {
			se.printStackTrace();
			throw new SQLException();
		} catch (NamingException ne) {
			ne.printStackTrace();
			throw new NamingException();
		}
	}

	public static int insertTestSpsSendDetail(byte[] fileBytes, String fileName) throws Exception {
		System.out.println("Enter insertTestSpsSendDetail ");
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql;

		try {
			con = getConnectionCivil();
			// DatabaseMetaData dm = con.getMetaData();
			// log.debug("driver name "+dm.getDriverName());
			// log.debug("product name"+dm.getDatabaseProductName());
			// log.debug("url"+dm.getURL());
			sql = " INSERT INTO SPS_SEND_DETAIL (sendwrit_book_no, photo, filename) " + " VALUES (?, ?, ?) ";
			System.out.println("[LED] sql = " + sql);
			System.out.println("sendwrit_book_no = " + "ยธ0522/(1)1209");
			// log.debug("photo = "+detail.getPhoto());
			log.debug("filename = " + fileName);
			pstmt = con.prepareStatement(sql);
			int k = 1;
			pstmt.setString(k++, "ยธ0522/(1)1209");
			pstmt.setBytes(k++, fileBytes);
			pstmt.setString(k++, fileName);
			int result = pstmt.executeUpdate();
			log.debug("result insertTestSpsSendDetail = " + result);
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("Error insertTestSpsSendDetail ", ex);
			throw ex;
		} finally {
			closeResource(con, pstmt, null);
			log.debug("[LED] Exit insertTestSpsSendDetail ");
		}
	}

	public static void closeResource(Connection con, Statement st, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
