package BF;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BFSQL {

	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	Connection con2 =  null;
	PreparedStatement pstmt2 = null;	
	ResultSet rs2 = null;

	public void connect() {
		con = DBC.DBConnect();
		con2 = DBC.DBConnect();
	}

	public void conClose() {
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 회원가입
		public void regist(Users users) {

			String sql = "INSERT INTO USERS VALUES(?,?,?,?,?,?,?,?)";

			try {
				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, users.getuId());
				pstmt.setString(2, users.getuPw());
				pstmt.setString(3, users.getuName());
				pstmt.setString(4, users.getuAddr());
				pstmt.setString(5, users.getuEmail());
				pstmt.setString(6, users.getuPhone());
				pstmt.setString(7, users.getuBirth());
				pstmt.setInt(8, users.getuCode());

				int result = pstmt.executeUpdate();

				if (result > 0) {
					System.out.println("회원가입 완료!");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// 이용자 정보 번호로 조회
		public String checkUserInfo(int num, String uId) {

			String sql = "SELECT U_ID, U_PW, U_NAME, U_ADDR, U_EMAIL, U_PHONE, U_BIRTH, U_CODE FROM USERS WHERE U_ID = ?";
			String value = null;
			boolean check = false;

			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, uId);
				rs = pstmt.executeQuery();

				if (rs.next()) {
					check = true;
					if (num == 8) {

						if (rs.getString(num) == null) {
							value = " ";
						} else {
							value = rs.getString(num);
						}

					} else {

						if (rs.getString(num) == null) {
							value = " ";
						} else {
							value = rs.getString(num);
							
						}
					}
				}else {
					value = " ";
				}
				
//				if(!check) {
//					value = " ";
//				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return value;

		}
		
		
		// 식당 정보 여부확인 및 출력
		public boolean categoryList(String dCate, String colum) {
			
			String cate = null;
			String dName = null;
			String dTime = null;
			String dMinPr = null;
			String uId = null;
			double rStar = 0;
			
			String sql = "SELECT * FROM DINER WHERE "+colum+"  = ?";
			String sql2 = "SELECT AVG(R_STAR) FROM DINER JOIN REVIEW ON DINER.D_NAME = REVIEW.D_NAME WHERE REVIEW.D_NAME = ?";
			
			boolean check = false;
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, dCate);
				rs = pstmt.executeQuery();

				while(rs.next()) {
					cate = rs.getString(1);
					dName = rs.getString(2);
					dTime = rs.getString(3);
					dMinPr = rs.getString(4);
					uId = rs.getString(5);

					pstmt2 = con2.prepareStatement(sql2);
					pstmt2.setString(1, dName);
					rs2 = pstmt2.executeQuery();
					
					if(rs2.next()) {
						check = true;
						rStar = rs2.getDouble(1);
						
					}else {
						rStar = 0;
						
					}
					
					System.out.println("[식당이름 : "+dName+", 별점 : "+Math.round(rStar*100)/100.0+"점"+", 운영시간 : "+dTime+", 최소주문금액 : "+dMinPr+", 판매자 : "+uId+"]");
					
				}
			
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
			return check;
		}
		
		
		// 메뉴 이름 맞는지 / 있는지 확인
		public boolean selectMenu(String mName) {
			
			boolean check = false;
			
			String sql = "SELECT M_NAME FROM MENU WHERE M_NAME = ?";
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, mName);
				
				rs = pstmt.executeQuery();
				
				check = rs.next();
				
				if(check) {
					rs.getString(1);
				}else {
					System.out.println("없는 메뉴입니다.");
				}
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			return check;
			
		}
	
		
		// 식당 이름으로 메뉴 목록 출력
		public boolean selectDiner(String dName) {
			
			String mName = null;
			int mPrice = 0;
			int mCnt = 0;
			String mDetail = null;
			boolean check = false;
			String sql = "SELECT * FROM MENU WHERE D_NAME = ?";

			try {
				pstmt = con.prepareStatement(sql);

				pstmt.setString(1, dName);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					check = true;
					mName = rs.getString(1);
					mPrice = rs.getInt(2);
					mDetail = rs.getString(3);
					dName = rs.getString(4);
					System.out.println("[메뉴이름 : " + mName + ", 가격 : " + mPrice + ", 소개 : " + mDetail + ", 식당이름 : " + dName + "]");
				}
				
				if (!check) {
					System.out.println("없는 식당이거나 메뉴가 등록되지 않았습니다.");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return check;
		}

		
		// 주문내역에 정보 추가
		public void orderList(String uId, OrderList ol, int price) {
			
			String sql = "INSERT INTO ORDERLIST VALUES(?,?,?,?,SYSDATE,?,?,?)";
			
			int num = orderListNumber() + 1;
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, uId);
				pstmt.setString(2, ol.getdName());
				pstmt.setString(3, ol.getmName());
				pstmt.setInt(4, num);
				pstmt.setInt(5, ol.getoCnt());
				pstmt.setString(6, ol.getoPay());
				pstmt.setInt(7, price);
				int result = pstmt.executeUpdate();
				if (result > 0) {
					System.out.println(uId + "님 주문 완료!");
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		
		// 리뷰목록
		public void checkReview(String dName) {

			String sql = "SELECT * FROM REVIEW WHERE D_NAME=?";

			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, dName);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					System.out.println("리뷰번호 : " + rs.getInt(1));
					System.out.println("이용자ID : " + rs.getString(2));
					System.out.println("식당이름 : " + rs.getString(3));
					System.out.println("리뷰내용 : " + rs.getString(4));
					System.out.println("별점 : " + rs.getInt(5));
					System.out.println("리뷰날짜 : " + rs.getString(6));
					System.out.println();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		
		// 결제 후 바로 출력되는 주문목록
		public void orderListView(Menu menu) {
			
			//주문번호로 조회하기
			String sql = "SELECT DISTINCT O_NUM, O.D_NAME, O.M_NAME, MENU.M_PRICE*O_CNT, O_CNT, O_DATE, O_PAY, U_ADDR FROM ORDERLIST O,USERS,MENU WHERE O.U_ID = USERS.U_ID AND O.D_NAME = MENU.D_NAME AND O_NUM = ? AND MENU.M_NAME = ?";
			int num = orderListNumber();
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, num);
				pstmt.setString(2, menu.getmName());
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					System.out.println("[주문번호 : "+rs.getInt(1)+", 가게 이름 : "+rs.getString(2)+", 메뉴 : "+rs.getString(3)+", 가격 : "+rs.getInt(4)+", 수량 : "+rs.getInt(5)+", 주문일자 : "+rs.getString(6)+", 결제수단 : "+rs.getString(7)+", 주소 : "+rs.getString(8));
				}
				
			}catch(SQLException e) {
				e.printStackTrace();
			}	
		}

		
		// 주문내역 (이용자)
		public void orderListAll(Users users) {
			
			String sql = "SELECT * FROM ORDERLIST WHERE U_ID = ?";
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, users.getuId());
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					System.out.println("[주문번호 : "+rs.getInt(4)+", 가게 이름 : "+rs.getString(2)+", 메뉴 : "+rs.getString(3)+", 가격 : "+rs.getInt(8)+", 수량 : "+rs.getInt(6)+", 주문일자 : "+rs.getString(5)+", 결제수단 : "+rs.getString(7));
				}
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		// 판매내역 (사업자)
		public void orderListAll(Diner diner) {
			
			String sql = "SELECT * FROM ORDERLIST WHERE U_ID = ?";
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, diner.getdName());
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					System.out.println("[주문번호 : "+rs.getInt(4)+", 가게 이름 : "+rs.getString(2)+", 메뉴 : "+rs.getString(3)+", 가격 : "+rs.getInt(8)+", 수량 : "+rs.getInt(6)+", 주문일자 : "+rs.getString(5)+", 결제수단 : "+rs.getString(7));
				}
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		// 주문번호 자동생성 (순차적)
		public int orderListNumber() {

			int num = 0;

			String sql = "SELECT COUNT(*) FROM ORDERLIST";

			try {
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					num += rs.getInt(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return num;
		}
		
		
		// 리뷰번호 자동생성 (순차적)
		public int reviewNumber() {
			
			int num = 0;

			String sql = "SELECT COUNT(*) FROM REVIEW";

			try {
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					num += rs.getInt(1);
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return num;
		}
		
		
		// 메뉴 이름으로 메뉴 가격 찾기
		public int menuPrice(Menu menu) {
			
			int price = 0;
			
			String sql ="SELECT M_PRICE FROM MENU WHERE M_NAME = ?";
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, menu.getmName());
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					price = rs.getInt(1);
				}
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
			return price;
		}
		
		
		// 리뷰등록
		public void insertReview(Users users, Diner diner, String str, int rStar) {
			
			String sql = "INSERT INTO REVIEW VALUES(?,?,?,?,?,SYSDATE)";
			
			int num = reviewNumber()+1;
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, num);
				pstmt.setString(2, users.getuId());
				pstmt.setString(3, diner.getdName());
				pstmt.setString(4, str);
				pstmt.setDouble(5, rStar);
			
				int result = pstmt.executeUpdate();
				
				if(result > 0) {
					System.out.println("리뷰 작성완료!");
				}
				
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		// 식당 등록
		public void addDiner(Diner diner) {
			
			String sql = "INSERT INTO DINER VALUES(?,?,?,?,?)";
			
			try {
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, diner.getdCate());
				pstmt.setString(2, diner.getdName());
				pstmt.setString(3, diner.getdTime());
				pstmt.setInt(4, diner.getdMinPr());
				pstmt.setString(5, diner.getuId());
				
				int result = pstmt.executeUpdate();
				
				if(result > 0) {
					System.out.println("식당등록 완료!");
				} else {
					System.out.println("식당등록 실패!");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
		// 메뉴 등록
		public void addMenu(Menu menu) {
			
			String sql = "INSERT INTO MENU VALUES(?,?,?,?)";
			
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, menu.getmName());
				pstmt.setInt(2, menu.getmPrice());
				pstmt.setString(3, menu.getmDetail());
				pstmt.setString(4, menu.getdName());
				
				int result = pstmt.executeUpdate();
				
				if(result > 0) {
					System.out.println("메뉴등록 완료!");
				} else {
					System.out.println("메뉴등록 실패!");
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	
		// 이용자 계좌번호 생성
			public void accountNum(Account account) {
				
				String sql = "INSERT INTO ACCOUNT VALUES(?,?,2100000000)";
				
				try {
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, account.getAccount());
					pstmt.setString(2, account.getuId());
					
					int result = pstmt.executeUpdate();
					
					if(result>0) {
						System.out.println("계좌생성 성공");
					} else {
						System.out.println("계좌생성 실패");
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			
			// 계좌이체 결제 (출금)
			public void pay(String id, Account account, int balance) {
				
				String sql = "UPDATE ACCOUNT SET BALANCE=BALANCE-? WHERE U_ID=?";
				
				try {
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, balance);
					pstmt.setString(2, account.getuId());
					
					int result = pstmt.executeUpdate();
					
					if(result>0) {
						System.out.println(balance + "원 결제");
					} else {
						System.out.println("결제 실패");
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
	}








