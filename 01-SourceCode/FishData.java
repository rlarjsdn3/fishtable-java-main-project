package databaseProject;

import java.util.Objects;
import java.util.Vector;

public class FishData {
	private static String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost/aquariumDB";
	
	// MySQL 아이디와 비밀번호는 회원가입 연산 수행을 대비하기 위해 
	// 기본적으로 루트 계정으로 설정되며 사용자가 로그인 화면에 새로 로그인하면 아래 데이터는 갱신된다.
	private static String id = "root", password = "@ch907678";
	
	private static int selectedRow;
	
	private static String commonName, scientificName, division, lastModifiedDate; // 이름, 학명, 분류, 마지막 수정
	private static String size, color, swimmingArea, aggresion, taste; // 최대 크기, 색상, 유영 영역, 공격성, 식
	private static String minimumCelsius, maximumCelsius, properPH, properGH; // 최소 온도, 최대 온도, 적정PH, 적정GH
	private static String breedDifficulty, breedingDifficulty; // 사육 난이도, 번식 난이도 
	private static String text; // 설명
	private static String imageFileName; // 이미지 파일 이름
	private static String imageFileDirectory =  "/Users/kimkunwoo/Dropbox/Mac (2)/Desktop/aquariumDBImage/"; // 이미지 파일 저장 경로
	
	private static Vector listOfCommonName;
	private static Vector listOfImageSourceName; // 미구현 
	 
	public static void setDriver(String s) {
		driver = s;
	}
	
	public static String getDriver() {
		return driver;
	}
	
	public static void setUrl(String s) {
		url = s;
	}
	
	public static String getUrl() {
		return url;
	}
	
	public static void setId(String s) {
		id = s;
	}
	
	public static String getId() {
		return id;
	}
	
	public static void setPassword(String s) {
		password = s;
	}
	
	public static String getPassword() {
		return password;
	}
	
	public static void setSelectedRow(int s) {
		selectedRow = s;
	}
	
	public static int getSelectedRow() {
		return selectedRow;
	}
	
	public static void setCommonName(String s) {
		commonName = s;
	}
	
	public static String getCommonName() {
		return Objects.nonNull(commonName) ? commonName : "";
	}
	
	public static void setScientificName(String s) {
		scientificName = s;
	}
	
	public static String getScientificName() {
		return Objects.nonNull(scientificName) ? scientificName : "";
	}
	
	public static void setDivision(String s) {
		division = s;
	}
	
	public static String getDivision() {
		return division;
	}
	
	public static void setLastModifiedDate(String s) {
		lastModifiedDate = s;
	}
	
	public static String getLastModifiedDate() {
		return lastModifiedDate;
	}
	
	public static void setSize(String s) {
		size = s;
	}
	
	public static String getSize() {
		return Objects.nonNull(size) ? size : "";
	}
	
	public static void setColor(String s) {
		color = s;
	}
	
	public static String getColor() {
		return Objects.nonNull(color) ? color : "";
	}
	
	public static void setSwimmingArea(String s) {
		swimmingArea = s;
	}
	
	public static String getSwimmingArea() {
		return swimmingArea;
	}
	
	public static void setAggresion(String s) {
		aggresion = s;
	}
	
	public static String getAggresion() {
		return aggresion;
	}
	
	public static void setTaste(String s) {
		taste = s;
	}
	
	public static String getTaste() {
		return taste;
	}
	
	public static void setMinimumCelsius(String s) {
		minimumCelsius = s;
	}
	
	public static String getMinimumCelsius() {
		return minimumCelsius;
	}
	
	public static void setMaximumCelsius(String s) {
		maximumCelsius = s;
	}
	
	public static String getMaximumCelsius() {
		return maximumCelsius;
	}
	
	public static void setProperPH(String s) {
		properPH = s;
	}
	
	public static String getProperPH() {
		return properPH;
	}
	
	public static void setProperGH(String s) {
		properGH = s;
	}
	
	public static String getProperGH() {
		return properGH;
	}
	
	public static void setBreedDifficulty(String s) {
		breedDifficulty = s;
	}
	
	public static String getBreedDifficulty() {
		return breedDifficulty;
	}
	
	public static void setBreedingDifficulty(String s) {
		breedingDifficulty = s;
	}
	
	public static String getBreedingDifficulty() {
		return breedingDifficulty;
	}
	
	public static void setText(String s) {
		text = s;
	}
	
	public static String getText() {
		return text;
	}
	
	public static void setListOfCommonName(Vector v) {
		listOfCommonName = v;
	}
	
	public static Vector getListOfCommonName() {
		return Objects.nonNull(listOfCommonName) ? listOfCommonName : null;
	}
	
	public static void setImageFileName(String s) {
		imageFileName = Objects.nonNull(s) ? s : "(이미지 없음)";
	}
	
	public static String getImageFileName() {
		return imageFileName;
	}
	
	public static void setImageFileDirectory(String s) {
		imageFileDirectory = s;
	}
	
	public static String getImageFileDirectory() {
		return imageFileDirectory;
	}
}
