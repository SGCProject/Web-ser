package tmp;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CdgDltMobileServiceWS implements CdgDltMobileService {
	private static transient Logger log = LogManager.getLogger(CdgDltMobileServiceWS.class);

	// ใช้ในการทำ json
	ObjectMapper mapper = new ObjectMapper();

	@Override
	public String registerUser(String idNo, String password) throws Exception {
		log.debug("Enter RegisterUser");
		StringBuilder sb = new StringBuilder();
		Map map = new HashMap();
		try {
			map = CdgDltMobileServiceDAO.registerUser(idNo, password);
		} catch (Exception e) {
			log.error("Error registerUser", e);
			map.put("result", "N");
			map.put("key", "เกิดความผิดพลาดในการ registerUser");
		} finally {
			log.debug("Exit registerUser");
		}
		Writer strWriter = new StringWriter();
		mapper.writeValue(strWriter, map);
		sb.append(strWriter.toString());
		return sb.toString();
	}

	@Override
	public String registerUser2(String idNo, String password) throws Exception {
		log.debug("Enter RegisterUser2");
		StringBuilder sb = new StringBuilder();
		Map map = new HashMap();
		try {
			map = CdgDltMobileServiceDAO.registerUser(idNo, password);
		} catch (Exception e) {
			log.error("Error registerUser2", e);
			map.put("result", "N");
			map.put("key", "เกิดความผิดพลาดในการ registerUser2");
		} finally {
			log.debug("Exit registerUser2");
		}
		Writer strWriter = new StringWriter();
		mapper.writeValue(strWriter, map);
		sb.append(strWriter.toString());
		return sb.toString();
	}

}
