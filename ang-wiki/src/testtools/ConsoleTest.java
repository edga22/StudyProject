package testtools;

import java.util.Scanner;
import system.*;


public class ConsoleTest {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		PostMgr postmg = new PostMgr();
		Posts post = new Posts();
		PostSearch postsh = new PostSearch();
		
		for(Posts a:postsh.getTitlePosts("테스트")){
			System.out.println(a.getTitle());
			System.out.println(a.getContent());
			System.out.println(a.getWriter());
			System.out.println();
		}
		in.close();
	}

}
