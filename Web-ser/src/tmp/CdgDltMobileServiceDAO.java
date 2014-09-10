package tmp;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class CdgDltMobileServiceDAO {
	private static transient Logger log = LogManager
			.getLogger(CdgDltMobileServiceDAO.class);

	public static Map registerUser(String idNo, String password) throws Exception {
		log.debug("Enter registerUser ");
		try {
			Map map = new HashMap();
			
			if(idNo.equals("abc") && password.equals("xyz")) {
				map.put("result", "Y");
				map.put("message", "ลงทะเบียนสำเร็จ");
			} else {
				map.put("result", "N");
				map.put("message", "idNo หรือ password ไม่ถูกต้อง");
			}

			return map;
		} catch (Exception ex) {
			log.error("Error registerUser ", ex);
			throw ex;
		} finally {
			log.debug("Exit registerUser ");
		}
	}

}
