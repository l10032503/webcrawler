import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class SQLTest {
    public SQLTest(String[][][] arr){

        String dbURL = "jdbc:mariadb://notebookwebservice.cjmajxgzxql6.ap-northeast-2.rds.amazonaws.com:3306/notebookdata";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, "l10032503", "20192019");

            int i_length = arr.length;
            int j_length = arr[0].length;

            /*
            //컬럼 추가(테이블이 id 컬럼을 기본으로 가지고 있을 때)
            for(int j=0 ; j<j_length ; j++)
                if(arr[0][j][0].length()>1){
                    if(j>=46 && j<50) //컬럼명 중복
                        pstmt = conn.prepareStatement("alter table for_crawling add column `" + arr[0][j][0] + "(종류)` varchar(100)");
                    else
                        pstmt = conn.prepareStatement("alter table for_crawling add column `" + arr[0][j][0] + "` varchar(100)");
                    rs = pstmt.executeQuery();
                }
             */

            //데이터 삽입
            for(int i=0 ; i<i_length ; i++){
                String strvalue = (i+1) + "";
                for(int j=0 ; j<j_length ; j++){
                    if(arr[0][j][0].length()>1)
                        strvalue += ","+"'"+arr[i][j][1]+"'";
                }
                pstmt = conn.prepareStatement("insert into for_crawling values(" + strvalue + ")");
                rs = pstmt.executeQuery();
            }

            while(rs.next()) {
                //
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally { //사용순서와 반대
            try {
                if(rs != null) {
                    rs.close(); // 선택
                }
                if(pstmt != null) {
                    pstmt.close(); // 선택
                }
                if(conn != null) {
                    conn.close(); // 필수
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
