
public class Test {

	public static void main(String[] args) {
		
		String name = "新疆维吾尔自治区";
		System.out.println("--》"+name.indexOf("新疆",0));
		if(name.indexOf("新疆")!=-1){
			System.out.println(name.substring(0,name.length()-1));
		}
	}
}
