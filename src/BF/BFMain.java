package BF;

import java.util.Scanner;

public class BFMain {

	public static void main(String[] args) {

		Users users = new Users();
		Diner diner = new Diner();
		Menu menu = new Menu();
		OrderList orderlist = new OrderList();
		Account account = new Account();
		Review review = new Review();
		BFSQL sql = new BFSQL();

		boolean run = true, run2 = true;
		boolean sRun = true;
		boolean cateRun = true;

		Scanner sc = new Scanner(System.in);

		String id, pw, pw2, name, addr, email, phone, birth;
		String dCate, dName, dTime;
		String mName, mDetail;
		String oDate, oPay;
		String rReview, rDate;
		String search;
		int dMinpr, mPrice, mCnt, oNum, rNum, rStar;
		int menu4 = 0, cnt = 0;
		
		sql.connect();

		while (run) {
			System.out.println("=============================");
			System.out.println("1.회원가입   2.로그인   3.종료");
			System.out.println("=============================");
			System.out.print("선택 >> ");
			int menu1 = sc.nextInt();

			switch (menu1) {
			case 1:
				System.out.println("1.이용자 회원가입     2.사업자 회원가입");
				System.out.print("선택 >> ");
				int menu2 = sc.nextInt();

				if (menu2 == 1 || menu2 == 2) {
					// 이용자 회원가입시
					System.out.print("아이디 >> ");
					id = sc.next();
					if (sql.checkUserInfo(1, id).equals(id)) {
						System.out.println("이미 존재하는 아이디입니다.");
						break;
					} else {
						users.setuId(id);
					}

					System.out.print("비밀번호 >> ");
					pw = sc.next();

					System.out.print("비밀번호 확인 >> ");
					pw2 = sc.next();

					if (pw.equals(pw2)) {
						users.setuPw(pw);
					} else {
						System.out.println("비밀번호가 서로 다릅니다!");
						break;
					}

					System.out.print("이름 >> ");
					name = sc.next();
					users.setuName(name);

					System.out.print("주소 >> ");
					addr = sc.next();
					users.setuAddr(addr);

					System.out.print("이메일 >> ");
					email = sc.next();
					users.setuEmail(email);

					System.out.print("연락처 >> ");
					phone = sc.next();
					users.setuPhone(phone);

					System.out.print("생년월일 >> ");
					birth = sc.next();
					users.setuBirth(birth);

					users.setuCode(menu2);
					sql.regist(users);

				} else {
					System.out.println("없는 메뉴입니다.");
				}

				break;

			case 2:
				System.out.println("아이디 >>");
				id = sc.next();

				if (id.equals(sql.checkUserInfo(1, id))) {
					users.setuId(id);

					System.out.print("비밀번호 >> ");
					pw = sc.next();

					if (pw.equals(sql.checkUserInfo(2, users.getuId()))) {
						System.out.println("로그인 성공!");

						if (sql.checkUserInfo(8, id).equals("1")) {
							System.out.println("이용자로 로그인");
							run2 = true;
							
							while (run2) {
								System.out.println("=================================");
								System.out.println("1.카테고리   2.식당검색   3.로그아웃");
								System.out.println("=================================");
								System.out.print("선택 >> ");
								int menu3 = sc.nextInt();

								switch (menu3) {
								case 1:
									System.out.println("==============================");
									System.out.println("1.한식   2.중식   3.양식   4.일식");
									System.out.println("==============================");
									System.out.print("선택 >> ");
									menu4 = sc.nextInt();
									
									switch (menu4) {
									case 1:
										if (sql.categoryList("한식", "D_CATE")) {
											System.out.print("식당 검색(식당이름) >> ");
											search = sc.next();

											if (sql.categoryList(search, "D_NAME")) {
												diner.setdName(search);
												orderlist.setdName(search);
												cateRun = true;
												
												while (cateRun) {
													System.out.println("=========================================================");
													System.out.println("1.리뷰보기   2.주문하기   3.주문내역   4.리뷰작성   5.종료");
													System.out.println("=========================================================");
													System.out.print("선택 >> ");
													int menu5 = sc.nextInt();
													
													switch (menu5) {
													case 1:
														// 리뷰목록
														sql.checkReview(diner.getdName());
														break;
														
													case 2:

														if(sql.selectDiner(diner.getdName())) {
															System.out.print("메뉴 선택(메뉴이름) >> ");
															mName = sc.next();
															
															if (sql.selectMenu(mName)) {
																menu.setmName(mName);
																orderlist.setmName(mName);
																
																System.out.print("수량 선택 >> ");
																mCnt = sc.nextInt();
																orderlist.setoCnt(mCnt);

																// 결제
																if (mName != null && mCnt != 0) {
																	System.out.println("결제방식 선택 (비대면)");
																	System.out.println("=================================");
																	System.out.println("1.카카오페이   2.카드결제   3.계좌이체");
																	System.out.println("=================================");
																	System.out.println("선택 >> ");
																	int menu6 = sc.nextInt();

																	switch (menu6) {
																	case 1:
																		System.out.println("카카오페이 비밀번호 >> ");
																		String kpw = sc.next();

																		if (kpw != null) {
																			System.out.println("결제 완료");
																			orderlist.setoPay("카카오페이");
																			sql.orderList(users.getuId(), orderlist, orderlist.getoCnt()*sql.menuPrice(menu));
																			sql.orderListView(menu);
																			
																		} else {
																			System.out.println("결제 실패 (비밀번호 재확인)");
																		}
																		
																		break;

																	case 2:
																		System.out.println("카드결제 비밀번호 >> ");
																		String cpw = sc.next();

																		if (cpw != null) {
																			System.out.println("결제 완료");
																			orderlist.setoPay("신용카드");
																			sql.orderList(users.getuId(), orderlist, orderlist.getoCnt()*sql.menuPrice(menu));
																			sql.orderListView(menu);
																			
																		} else {
																			System.out.println("결제 실패 (비밀번호 재확인)");
																		}
																		
																		break;

																	case 3:
																		// 계좌번호 생성
																		System.out.println("이용자 계좌번호 >> ");
																		String bAccount = sc.next();

																		account.setAccount(bAccount);
																		account.setuId(users.getuId());
																		
																		sql.accountNum(account);
																		
																		if(bAccount!=null) {
																			System.out.println("사업자 계좌번호 >> ");
																			String sAccount = sc.next();
																			
																			orderlist.setoPay("계좌이체");
																			sql.pay(id, account, orderlist.getoCnt()*sql.menuPrice(menu));
																			
																			sql.orderList(users.getuId(), orderlist, orderlist.getoCnt()*sql.menuPrice(menu));
																			sql.orderListView(menu);
																		}
																		
																		break;

																	default:
																		System.out.println("다시 입력");
																		break;
																	}
																}
																
															}else {
																
																break;
															}
															
														}else {
											
															break;
														}

														break;
														
													case 3:	
														sql.orderListAll(users);
														break;
														
													case 4:
														System.out.println("리뷰 작성 >> ");
														rReview = sc.next();

														System.out.println("별점 (1~5) >> ");
														rStar = sc.nextInt();

														review.setrReview(rReview);
														review.setrStar(rStar);
														sql.insertReview(users, diner, rReview, rStar);
														 
														break;
														
													case 5:
														cateRun = false;
														System.out.println("종료 합니다.");
														break;
														
													default:
														System.out.println("없는 메뉴입니다.");
														break;
													}
												}
												
											} else {
												System.out.println("식당이 없어요!!!");
												break;
											}
											
										} else {
											System.out.println("식당이 없어요!!!");
										}

										break;
										
									case 2:
										if (sql.categoryList("중식", "D_CATE")) {
											System.out.print("식당 검색(식당이름) >> ");
											search = sc.next();

											if (sql.categoryList(search, "D_NAME")) {
												diner.setdName(search);
												orderlist.setdName(search);
												cateRun = true;
												
												while (cateRun) {
													System.out.println("=========================================================");
													System.out.println("1.리뷰보기   2.주문하기   3.주문내역   4.리뷰작성   5.종료");
													System.out.println("=========================================================");
													System.out.print("선택 >> ");
													int menu5 = sc.nextInt();
													
													switch (menu5) {
													case 1:
														// 리뷰목록
														sql.checkReview(diner.getdName());
														break;
														
													case 2:
														if(sql.selectDiner(diner.getdName())) {
															System.out.print("메뉴 선택(메뉴이름) >> ");
															mName = sc.next();
															
															if (sql.selectMenu(mName)) {
																menu.setmName(mName);
																orderlist.setmName(mName);
																
																System.out.print("수량 선택 >> ");
																mCnt = sc.nextInt();
																orderlist.setoCnt(mCnt);

																if (mName != null && mCnt != 0) {
																	System.out.println("결제방식 선택 (비대면)");
																	System.out.println("=================================");
																	System.out.println("1.카카오페이   2.카드결제   3.계좌이체");
																	System.out.println("=================================");
																	System.out.println("선택 >> ");
																	int menu6 = sc.nextInt();

																	switch (menu6) {
																	case 1:
																		System.out.println("카카오페이 비밀번호 >> ");
																		String kpw = sc.next();

																		if (kpw != null) {
																			System.out.println("결제 완료");
																			orderlist.setoPay("카카오페이");
																			sql.orderList(users.getuId(), orderlist, orderlist.getoCnt()*sql.menuPrice(menu));
																			sql.orderListView(menu);
																			
																		} else {
																			System.out.println("결제 실패 (비밀번호 재확인)");
																		}
																		
																		break;

																	case 2:
																		System.out.println("카드결제 비밀번호 >> ");
																		String cpw = sc.next();

																		if (cpw != null) {
																			System.out.println("결제 완료");
																			orderlist.setoPay("신용카드");
																			sql.orderList(users.getuId(), orderlist, orderlist.getoCnt()*sql.menuPrice(menu));
																			sql.orderListView(menu);
																			
																		} else {
																			System.out.println("결제 실패 (비밀번호 재확인)");
																		}
																		
																		break;

																	case 3:
																		// 계좌번호 생성
																		System.out.println("이용자 계좌번호 >> ");
																		String bAccount = sc.next();

																		account.setAccount(bAccount);
																		account.setuId(users.getuId());
																		
																		sql.accountNum(account);
																		
																		if(bAccount!=null) {
																			System.out.println("사업자 계좌번호 >> ");
																			String sAccount = sc.next();
																			orderlist.setoPay("계좌이체");
																			sql.pay(id, account, orderlist.getoCnt()*sql.menuPrice(menu));
																			
																			sql.orderList(users.getuId(), orderlist, orderlist.getoCnt()*sql.menuPrice(menu));
																			sql.orderListView(menu);
																		}
																		
																		break;
																		
																	default:
																		System.out.println("다시 입력");
																		break;
																	}
																}
																
															}else {
																
																break;
															}
															
														}else {
											
															break;
														}
														
														break;
														
													case 3:	
														sql.orderListAll(users);
														break;
														
													case 4:
														System.out.println("리뷰 작성 >> ");
														rReview = sc.next();

														System.out.println("별점 (1~5) >> ");
														rStar = sc.nextInt();

														review.setrReview(rReview);
														review.setrStar(rStar);
														sql.insertReview(users, diner, rReview, rStar);
														
														break;
														
													case 5:
														cateRun = false;
														System.out.println("종료 합니다.");
														break;
														
													default:
														System.out.println("없는 메뉴입니다.");
														break;
													}
												}
												
											} else {
												System.out.println("식당이 없어요!!!");
												break;
											}
											
										} else {
											System.out.println("식당이 없어요!!!");
										}

										break;
										
									case 3:
										if (sql.categoryList("양식", "D_CATE")) {
											System.out.print("식당 검색(식당이름) >> ");
											search = sc.next();

											if (sql.categoryList(search, "D_NAME")) {
												diner.setdName(search);
												orderlist.setdName(search);
												cateRun = true;
												
												while (cateRun) {
													System.out.println("=========================================================");
													System.out.println("1.리뷰보기   2.주문하기   3.주문내역   4.리뷰작성    5.종료");
													System.out.println("=========================================================");
													System.out.print("선택 >> ");
													int menu5 = sc.nextInt();
													
													switch (menu5) {
													case 1:
														// 리뷰목록
														sql.checkReview(diner.getdName());
														break;
														
													case 2:
														if(sql.selectDiner(diner.getdName())) {
															System.out.print("메뉴 선택(메뉴이름) >> ");
															mName = sc.next();
															
															if (sql.selectMenu(mName)) {
																menu.setmName(mName);
																orderlist.setmName(mName);
																
																System.out.print("수량 선택 >> ");
																mCnt = sc.nextInt();

																orderlist.setoCnt(mCnt);

																if (mName != null && mCnt != 0) {
																	System.out.println("결제방식 선택 (비대면)");
																	System.out.println("=================================");
																	System.out.println("1.카카오페이   2.카드결제   3.계좌이체");
																	System.out.println("=================================");
																	System.out.println("선택 >> ");
																	int menu6 = sc.nextInt();

																	switch (menu6) {
																	case 1:
																		System.out.println("카카오페이 비밀번호 >> ");
																		String kpw = sc.next();

																		if (kpw != null) {
																			System.out.println("결제 완료");
																			orderlist.setoPay("카카오페이");
																			sql.orderList(users.getuId(), orderlist, orderlist.getoCnt()*sql.menuPrice(menu));
																			sql.orderListView(menu);
																			
																		} else {
																			System.out.println("결제 실패 (비밀번호 재확인)");
																		}
																		
																		break;

																	case 2:
																		System.out.println("카드결제 비밀번호 >> ");
																		String cpw = sc.next();

																		if (cpw != null) {
																			System.out.println("결제 완료");
																			orderlist.setoPay("신용카드");
																			sql.orderList(users.getuId(), orderlist, orderlist.getoCnt()*sql.menuPrice(menu));
																			sql.orderListView(menu);
																			
																		} else {
																			System.out.println("결제 실패 (비밀번호 재확인)");
																		}
																		
																		break;

																	case 3:
																		// 계좌번호 생성
																		System.out.println("이용자 계좌번호 >> ");
																		String bAccount = sc.next();

																		account.setAccount(bAccount);
																		account.setuId(users.getuId());
																		
																		sql.accountNum(account);
																		
																		if(bAccount!=null) {
																			System.out.println("사업자 계좌번호 >> ");
																			String sAccount = sc.next();
																			orderlist.setoPay("계좌이체");
																			sql.pay(id, account, orderlist.getoCnt()*sql.menuPrice(menu));
																			
																			sql.orderList(users.getuId(), orderlist, orderlist.getoCnt()*sql.menuPrice(menu));
																			sql.orderListView(menu);
																		}
																		
																		break;
																		
																	default:
																		System.out.println("다시 입력");
																		break;
																	}
																}
																
															}else {
																break;
															}
															
														}else {
											
															break;
														}
														
														break;
														
													case 3:	
														sql.orderListAll(users);
														break;
														
													case 4:
														System.out.println("리뷰 작성 >> ");
														rReview = sc.next();

														System.out.println("별점 (1~5) >> ");
														rStar = sc.nextInt();

														review.setrReview(rReview);
														review.setrStar(rStar);
														sql.insertReview(users, diner, rReview, rStar);
														
														break;
														
													case 5:
														cateRun = false;
														System.out.println("종료 합니다.");
														break;
														
													default:
														System.out.println("없는 메뉴입니다.");
														break;
													}
												}
												
											} else {
												System.out.println("식당이 없어요!!!");
												break;
											}
											
										} else {
											System.out.println("식당이 없어요!!!");
										}

										break;
										
									case 4:
										if (sql.categoryList("일식", "D_CATE")) {
											System.out.print("식당 검색(식당이름) >> ");
											search = sc.next();

											if (sql.categoryList(search, "D_NAME")) {
												diner.setdName(search);
												orderlist.setdName(search);
												cateRun = true;
												
												while (cateRun) {
													System.out.println("=========================================================");
													System.out.println("1.리뷰보기   2.주문하기   3.주문내역   4.리뷰작성   5.종료");
													System.out.println("=========================================================");
													System.out.print("선택 >> ");
													int menu5 = sc.nextInt();
													
													switch (menu5) {
													case 1:
														// 리뷰목록
														sql.checkReview(diner.getdName());
														break;
														
													case 2:
														if(sql.selectDiner(diner.getdName())) {
															System.out.print("메뉴 선택(메뉴이름) >> ");
															mName = sc.next();
															
															if (sql.selectMenu(mName)) {
																menu.setmName(mName);
																orderlist.setmName(mName);
																
																System.out.print("수량 선택 >> ");
																mCnt = sc.nextInt();
																orderlist.setoCnt(mCnt);

																if (mName != null && mCnt != 0) {
																	System.out.println("결제방식 선택 (비대면)");
																	System.out.println("=================================");
																	System.out.println("1.카카오페이   2.카드결제   3.계좌이체");
																	System.out.println("=================================");
																	System.out.println("선택 >> ");
																	int menu6 = sc.nextInt();

																	switch (menu6) {
																	case 1:
																		System.out.println("카카오페이 비밀번호 >> ");
																		String kpw = sc.next();

																		if (kpw != null) {
																			System.out.println("결제 완료");
																			orderlist.setoPay("카카오페이");
//																			System.out.println("users "+users.getuId());
//																			System.out.println("orderlist "+orderlist.getdName());
//																			System.out.println("orderlist "+orderlist.getmName());
//																			System.out.println("orderlist "+orderlist.getoCnt());
//																			System.out.println("menu "+menu.getmName());
																			sql.orderList(users.getuId(), orderlist, orderlist.getoCnt()*sql.menuPrice(menu));
																			sql.orderListView(menu);
																			
																		} else {
																			System.out.println("결제 실패 (비밀번호 재확인)");
																		}
																		
																		break;

																	case 2:
																		System.out.println("카드결제 비밀번호 >> ");
																		String cpw = sc.next();

																		if (cpw != null) {
																			System.out.println("결제 완료");
																			orderlist.setoPay("신용카드");
																			sql.orderList(users.getuId(), orderlist, orderlist.getoCnt()*sql.menuPrice(menu));
																			sql.orderListView(menu);
																			
																		} else {
																			System.out.println("결제 실패 (비밀번호 재확인)");
																		}
																		
																		break;

																	case 3:
																		// 계좌번호 생성
																		System.out.println("이용자 계좌번호 >> ");
																		String bAccount = sc.next();

																		account.setAccount(bAccount);
																		account.setuId(users.getuId());
																		
																		sql.accountNum(account);
																		
																		if(bAccount!=null) {
																			System.out.println("사업자 계좌번호 >> ");
																			String sAccount = sc.next();
																			orderlist.setoPay("계좌이체");
																			sql.pay(id, account, orderlist.getoCnt()*sql.menuPrice(menu));
																			
																			sql.orderList(users.getuId(), orderlist, orderlist.getoCnt()*sql.menuPrice(menu));
																			sql.orderListView(menu);
																		}
																		
																		break;
																		
																	default:
																		System.out.println("다시 입력");
																		break;
																	}
																}
																
															}else {
																
																break;
															}
															
														}else {
											
															break;
														}
														
														break;
														
													case 3:	
														sql.orderListAll(users);
														break;
														
													case 4:
														System.out.println("리뷰 작성 >> ");
														rReview = sc.next();

														System.out.println("별점 (1~5) >> ");
														rStar = sc.nextInt();

														review.setrReview(rReview);
														review.setrStar(rStar);
														sql.insertReview(users, diner, rReview, rStar);
														
														break;
														
													case 5:
														cateRun = false;
														System.out.println("종료 합니다.");
														break;
														
													default:
														System.out.println("없는 메뉴입니다.");
														break;
													}
												}
												
											} else {
												System.out.println("식당이 없어요!!!");
												break;
											}
											
										} else {
											System.out.println("식당이 없어요!!!");
										}

										break;
										
									case 5:
										run2 = false;
										System.out.println("종료합니다.");
										break;

									default:
										System.out.println("다시 선택하세요.");
										break;

									}
									
									break;

								case 2:
									System.out.print("식당 검색(식당이름) >> ");
									dName = sc.next();
//									if (sql.selectDiner(dName)) {
//										diner.setdNmae(dName);
//										orderlist.setdName(dName);
////										sql.list(dName);
//										sql.categoryList(dName,"D_NAME");
//									}
									if (sql.categoryList(dName, "D_NAME")) {
										diner.setdName(dName);
										orderlist.setdName(dName);
										
										if (dName != null) {
											System.out.println("===============================================");
											System.out.println("1.리뷰보기   2.주문하기   3.주문내역   4.리뷰작성");
											System.out.println("===============================================");
											System.out.print("선택 >> ");
											int menu5 = sc.nextInt();

											if (menu5 == 1) {
												// 리뷰목록
												sql.checkReview(dName);

											} else if (menu5 == 2) { // 주문하기

												if(sql.selectDiner(diner.getdName())) {
													System.out.print("메뉴 선택(메뉴이름) >> ");
													mName = sc.next();
													
													if (sql.selectMenu(mName)) {
														menu.setmName(mName);
														orderlist.setmName(mName);
														
														System.out.print("수량 선택 >> ");
														mCnt = sc.nextInt();
														orderlist.setoCnt(mCnt);

														if (mName != null && mCnt != 0) {
															System.out.println("결제방식 선택 (비대면)");
															System.out.println("=================================");
															System.out.println("1.카카오페이   2.카드결제   3.계좌이체");
															System.out.println("=================================");
															System.out.println("선택 >> ");
															int menu6 = sc.nextInt();

															switch (menu6) {
															case 1:
																System.out.println("카카오페이 비밀번호 >> ");
																String kpw = sc.next();

																if (kpw != null) {
																	System.out.println("결제 완료");
																	orderlist.setoPay("카카오페이");
																	sql.orderList(users.getuId(), orderlist, orderlist.getoCnt()*sql.menuPrice(menu));
																	sql.orderListView(menu);
																	
																} else {
																	System.out.println("결제 실패 (비밀번호 재확인)");
																}
																
																break;

															case 2:
																System.out.println("카드결제 비밀번호 >> ");
																String cpw = sc.next();

																if (cpw != null) {
																	System.out.println("결제 완료");
																	orderlist.setoPay("신용카드");
																	sql.orderList(users.getuId(), orderlist, orderlist.getoCnt()*sql.menuPrice(menu));
																	sql.orderListView(menu);
																	
																} else {
																	System.out.println("결제 실패 (비밀번호 재확인)");
																}
																
																break;

															case 3:
																// 계좌번호 생성
																System.out.println("이용자 계좌번호 >> ");
																String bAccount = sc.next();

																account.setAccount(bAccount);
																account.setuId(users.getuId());
																
																sql.accountNum(account);
																
																if(bAccount!=null) {
																	System.out.println("사업자 계좌번호 >> ");
																	String sAccount = sc.next();
																	orderlist.setoPay("계좌이체");
																	sql.pay(id, account, orderlist.getoCnt()*sql.menuPrice(menu));
																	
																	sql.orderList(users.getuId(), orderlist, orderlist.getoCnt()*sql.menuPrice(menu));
																	sql.orderListView(menu);
																}
																
																break;

															default:
																System.out.println("다시 입력");
																break;
															}
														}
													}
													
												}else {
													
													break;
												}
												
											} else if (menu5 == 3) {
												// 주문내역
												sql.orderListAll(users);

											} else if (menu5 == 4) {
												// 리뷰작성 / 별점매기기
												System.out.println("리뷰 작성 >> ");
												rReview = sc.next();

												System.out.println("별점 (1~5) >> ");
												rStar = sc.nextInt();

												review.setrReview(rReview);
												review.setrStar(rStar);
												sql.insertReview(users, diner, rReview, rStar);
												
											} else {
												System.out.println("다시 입력");
											}
										}
										
									} else {
										System.out.println("식당이 없어요!!!");
									}
									
									break;
									
								case 3:
									System.out.println("로그아웃 되었습니다!");
									run2 = false;
									break;

								}
							}
							
						} else {
			
							System.out.println("사업자로 로그인");
							sRun = true;
							
							while(sRun) {
								System.out.println("===============================");
								System.out.println("1.식당등록   2.메뉴등록   3.판매내역");
								System.out.println("4.식당목록   5.리뷰목록   6.로그아웃");
								System.out.println("===============================");
								System.out.print("선택 >> ");
								int menu7 = sc.nextInt();
								
								
								switch (menu7) {
								case 1:
									// 식당 등록
									diner.setuId(users.getuId());

									System.out.print("식당이름 >> ");
									dName = sc.next();
									diner.setdName(dName);

									System.out.print("식당종류(한식 / 중식 / 일식 / 양식) >> ");
									System.out.println();
									dCate = sc.next();
									diner.setdCate(dCate);

									System.out.print("주문가능시간 >> ");
									dTime = sc.next();
									diner.setdTime(dTime);

									System.out.print("최소주문금액 >> ");
									dMinpr = sc.nextInt();
									diner.setdMinPr(dMinpr);

									sql.addDiner(diner);

									break;

								case 2:
									// 메뉴 등록
									System.out.print("식당 이름을 입력하세요 >> ");
									dName = sc.next();
									menu.setdName(dName);

									if (dName.equals(dName)) {
										System.out.print("메뉴 이름을 입력하세요 >> ");
										mName = sc.next();
										menu.setmName(mName);

										System.out.print("메뉴 가격을 입력하세요 >> ");
										mPrice = sc.nextInt();
										menu.setmPrice(mPrice);

										System.out.print("메뉴 설명을 입력하세요(최대 30자) >> ");
										mDetail = sc.next();
										menu.setmDetail(mDetail);
										sql.addMenu(menu);

									} else {
										System.out.println();
										System.out.println("등록된 식당이 아닙니다!");
									}

									break;

								case 3:
									// 판매 내역
									sql.orderListAll(users);
									break;

								case 4:
									// 식당 목록
									sql.categoryList(users.getuId(), "U_ID");
									break;

								case 5:
									// 리뷰 목록
									sql.checkReview(diner.getdName());
									break;

								case 6:
									sRun = false;
									System.out.println("메인 메뉴로 이동합니다.");
									break;

								default:
									System.out.println("다시 입력해주세요");
									break;
								}
							}
						}
						
					} else {
						System.out.println("비밀번호가 다릅니다!");
					}

				} else {
					System.out.println("없는 아이디입니다!");
				}

				break;

			case 3:
				System.out.println("종료 합니다.");
				run = false;
				break;

			default:
				System.out.println("다시 선택해주세요.");
				break;
			}
		}

	}

}

