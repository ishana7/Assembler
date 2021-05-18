
import java.io.*; 
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner; 
import java.util.StringTokenizer;
import java.io.PrintWriter; 


public class Apass1 {
	
	static int LC;
	static List<Symtable> ST;
	static List<Literal> LT;
	static PrintWriter writer1;
    static void init()
    {
    	LC=0;
    	ST=new LinkedList<>();
    	LT=new LinkedList<>();
    }
	public static void pass1() {
		init();
		try{
		File file = new File("ip.asm");
		Scanner sc = new Scanner(file); 
		writer1 = new PrintWriter( new File("output.txt"));
		  
	    while (sc.hasNextLine()) {
	      
	      String s = sc.nextLine();
	      System.out.println(s);
	      StringTokenizer st = new StringTokenizer(s);
	      String arr1[] = new String[st.countTokens()];
	      
	      int i = 0;
	      //System.out.println(sc.nextLine()); 
	      while(st.hasMoreTokens()){
	    	  arr1[i++] = st.nextToken();
	    	  
	    }
	      int flag = search_pot(arr1);
    	  if (flag == 0)
    	  {
    		  //System.out.println("mot"); 
    		  int flag1 = search_mot(arr1);
    		  if (flag1 == 1)
    		  {
    			  System.out.println("MOT Search Successful !!"); 
    		  }
    		 
    	  }
    	  else
    	  {
    		  System.out.println("POT Search Successful!!"); 
    	  }
	      //System.out.println(Arrays.toString(arr1)); 
	      writer1.println(s);
		  writer1.flush();
		  //writer1.close();
		}
	    int count;
	    System.out.println("\n Symbol table\n");
	    for(count=0;count<ST.size();count++)
	    {
	    	System.out.println(ST.get(count).Sname+"\t"+ST.get(count).Add+"\t"+ST.get(count).len+"\t"+ST.get(count).rel);
	    }
	    System.out.println("\n Literal table\n");
	    for(count=0;count<LT.size();count++)
	    {
	    	System.out.println(LT.get(count).Lname+"\t"+LT.get(count).LAdd+"\t"+LT.get(count).Llen+"\t"+LT.get(count).Lrel);
	    }
	    sc.close();
	    
		}
	    catch(Exception e)
		{
			System.out.println(" Exception occured!"); 
		}
	}
	
	public static int searchST(String s)
	{
		for(int i=0;i<ST.size();i++)
		{
			if(s.equals(ST.get(i).Sname))
					return i;
				
		}
		
		return -1;
		
	}
	
	public static int searchLT(String t)
	{
		for(int i=0;i<LT.size();i++)
		{
			if(t.equals(LT.get(i).Lname))
					return i;
				
		}
		
		return -1;
		
	}
	
	public static int search_pot(String[] arr1) {
		int loc = 0;
		int l=0;
		int flag = 0;
		
		if(arr1.length == 3)
		{
			loc = 1;
		}
		
	    if (arr1[loc].equals("START"))
	    {
	    	System.out.println("Start Procedure called.."); 
	    	LC = Integer.parseInt(arr1[2]);
	    	writer1.print(LC+" ");
	    	System.out.println(LC); 
	    	ST.add(new Symtable(arr1[0],LC,1,'R'));
	    	flag = 1;
	    }
	    
	    else if(arr1[loc].equals("USING"))
	    {
	    	System.out.println("USING Procedure called..");
	    	writer1.print(LC+" ");
	    	System.out.println(LC); 
	    	flag = 1;
	    }
	    
	    else if(arr1[loc].equals("LTORG"))
		{
			System.out.println("LTORG found..."); 
			writer1.println(LC+"   \tUpto this Wasted");
			
			while(LC%8 !=0)
			{
				LC++;
			}
			writer1.print(LC+" ");
			for(int w=0;w<LT.size();w++)
			{
				if(LT.get(w).LAdd == -1)
				{
					LT.set(w,new Literal(LT.get(w).Lname,LC,LT.get(w).Llen,'R'));
					l = LT.get(w).Llen;
					System.out.println(l);  
					System.out.println("Added in literal table "); 
				}
			}
			LC+=l;
			System.out.println(LC);  
			//writer1.print(LC+" ");
			flag = 1;
	
		}
	    
		else if(arr1[loc].equals("DS"))
		{
			writer1.print(LC+" ");
			System.out.println(LC);  
			System.out.println("DS found...");
			
            int x=searchST(arr1[0]);
			
			
			if(x==-1)
			{
				
				int a = arr1[2].indexOf('F');
				System.out.println(a+"...index");
				if(a>0)
				{
					String substr1 = arr1[2].substring(0,a);
					l= Integer.parseInt(substr1);
					l=l*4;
					
					//System.out.println(l); 
				}
				else
				{
					System.out.println("in else loop...");
					l = 4;
					
				}
				System.out.println(l); 
				System.out.println(" hi.."); 
				ST.add(new Symtable(arr1[0],LC,l,'R'));
			    LC = LC + l;
			    System.out.println(LC);  
			}
			
			else
			{
				int a =arr1[2].indexOf("F");
				System.out.println(a);
				if(a>0)
				{
					String substr=arr1[2].substring(0, a);
					System.out.println(substr);    
					l= Integer.parseInt(substr);
					l=l*4;
				}
				else
				{
					l = 4;
				}
				
                ST.set(x, new Symtable(arr1[0],LC,l,'R')); 
                LC += l;
                System.out.println(LC);    
                flag = 1;
			}
	
		}
	    
		else if(arr1[loc].equals("DC"))
		{
			writer1.print(LC+" ");
			 System.out.println(LC);
			System.out.println("DC found...");
			
		
			int x=searchST(arr1[0]);
			
			
			if(x==-1)
			{
				
				int a = arr1[2].indexOf('F');
				//System.out.println(a+"...index");
				if(a>0)
				{
					String substr=arr1[2].substring(0,a);
					System.out.println(substr); 
					l= Integer.parseInt(substr);
					l=l*4;
				}
				else
				{
					l=4;
				}
				//System.out.println(l); 
			    ST.add(new Symtable(arr1[0],LC,l,'R'));
			    LC += l;
			}
			else
			{
				int a =arr1[2].indexOf("F");
				System.out.println(a);
				if(a>0)
				{
					String substr=arr1[2].substring(0,a);
					System.out.println(substr);    
					l= Integer.parseInt(substr);
					l=l*4;
				}
				else
				{
					l = 4;
				}
				
                ST.set(x, new Symtable(arr1[0],LC,l,'R')); 
                //LC += l;
                flag = 1;
			}
		}
		
		else if(arr1[loc].equals("END"))
		{
			System.out.println("END found....");
            writer1.println(LC+"\tUpto this Wasted");
			
			while(LC%8 !=0)
			{
				LC++;
			}
			writer1.println(LC+" ");
			for(int w=0;w<LT.size();w++)
			{
				if(LT.get(w).LAdd == -1)
				{
					LT.set(w,new Literal(LT.get(w).Lname,LC,LT.get(w).Llen,'R'));
					l = LT.get(w).Llen;
					System.out.println("Added in literal table "); 
				}
			}
			LC+=l;
			writer1.print(LC+" ");
			flag = 1;
	
		}
	    
	    
	     
		return flag;
	}
	
	public static int search_mot(String[] arr1) {
		int loc = 0;
		int flag = 0;
		int r;
		try{
			
			File file1 = new File("mot.txt");
			Scanner sc1 = new Scanner(file1);
			if(arr1.length == 3)
			{
				loc = 1;
				r=searchST(arr1[0]);
				ST.set(r, new Symtable(arr1[0],LC,0,'R'));
			}
			
			 while (sc1.hasNextLine())
			 {
			      
			    String s = sc1.nextLine();
			    StringTokenizer st1 = new StringTokenizer(s);
				if (arr1[loc].equals(st1.nextToken()))
				{
					writer1.print(LC+" ");
					st1.nextToken();
					int len = Integer.parseInt(st1.nextToken());
					String format = st1.nextToken();
				
					if(format.equals("RR"))
					{
						System.out.println("RR format found... ");
					}
					
					else if(format.equals("RX"))
					{
						System.out.println("RX format found... ");
					    StringTokenizer l1 = new StringTokenizer(arr1[1],",");
					    String op1 = l1.nextToken();
					    String op2 = l1.nextToken();
		
					    if (op2.startsWith("="))
					    {
					    	int y = searchLT(op2);
					    	if(y == -1)
					    	{
						    	LT.add(new Literal(op2,-1,4,'R'));
								System.out.println("Added in literal table "); 

					    	}
					    	else
					    	{
						    	LT.set(y,new Literal(op2,-1,4,'R'));
						    	System.out.println("Present in literal table "); 

					    	}
					    }
					    else
					    {
					    	int y = searchLT(op2);
					    	if(y == -1)
					    	{
						    	ST.add(new Symtable(op2,-1,4,'R'));
								System.out.println("Added in literal table "); 

					    	}
					    	else
					    	{
						    	ST.set(y,new Symtable(op2,-1,4,'R'));
						    	System.out.println("Present in literal table "); 

					    	}
					    }
					   

					}
					LC+=len;
			    	flag = 1;
				}
						
		    }
			 sc1.close();
			return flag;
		}
		catch(Exception e){
			
			System.out.println("Exception........... "); 
		}
		
		return flag;
	}
	
	public static void main(String[] args) throws FileNotFoundException{
		
		pass1();
	}
}

