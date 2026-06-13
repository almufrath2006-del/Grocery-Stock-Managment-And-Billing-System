import java.util.*;
public class Grocery {
    int product;
    String product_name;
    double product_price;
    double product_quantity;
    Grocery(int id){
        product=id;  
    }
    void prname(String prdnm){
        product_name=prdnm;
    }
    void prprc(double prdprc){
        product_price=prdprc;
    }
    void prq(double prdqnt){
        product_quantity=prdqnt;
    }
    void viewprd(){
        System.out.println("Product name:"+product_name);
        System.out.println("Product price:"+product_price);
        System.out.println("Product quantity:"+product_quantity);
    }
}
class Menu{
    Map<Integer,Grocery>prd=new LinkedHashMap<>();
    List<String>bill=new ArrayList<>();
    List<Integer>carInt=new ArrayList<>();
    List<Double>carDbl=new ArrayList<>();    
    List<String>sd=new ArrayList<>();
    int item=0;
    int f=0;
    double tprce=0;
    double wallet;
    double tsv=0;
    double disprc=0;
    void addpr(int prdid,String prdnm,double prdqnt,double prdprc){
        prd.put(prdid,(new Grocery(prdid)));
        prd.get(prdid).prname(prdnm);
        prd.get(prdid).prprc(prdprc);
        prd.get(prdid).prq(prdqnt);
        System.out.println("Product Added to stock.");
        tsv+=prdprc*prdqnt;
    }
    void viwe_productID(int prdid){
        if(prd.containsKey(prdid)){
            prd.get(prdid).viewprd();
        }
        else{ 
            System.out.println("No product found!");
        }
    }
    void viwe_productName(String prnm){
        int m=0;
        for (int k : prd.keySet()) {
            if(prd.get(k).product_name.equals(prnm)){
                prd.get(k).viewprd();
            }
            else{
                m++;
            }
            if(m==prd.size()){
                System.out.println("No Product found");
            }
        }
    }
    void viwe_all_product(){
        for (Integer i:prd.keySet()) {
            System.out.println("ID:"+i+" NAME:"+prd.get(i).product_name+" PRICE:"+prd.get(i).product_price+" QNT:"+prd.get(i).product_quantity);
        }
        System.out.println("Total products:"+prd.size());
        System.out.println("Total Stock value:"+tsv);
    }
    void bill_product(int prdid,double quantity){
        carInt.add(prdid);carDbl.add(quantity);
        item++; 
        prd.get(prdid).product_quantity-=quantity;
        double price= quantity*prd.get(prdid).product_price;
        tprce+=price;
        bill.add("ID:"+prdid+" PRODUCT:"+prd.get(prdid).product_name+" PRICE:"+prd.get(prdid).product_price+" QNT:"+quantity+" PRICE:"+price);f++;
        System.out.println(quantity+" "+prd.get(prdid).product_name+" Added to your cart!");
    }
    void AddCartInSd(){
        if(tprce>1000) {
            wallet+=disprc;
            for (String string : bill) {
            sd.add("Prodect selled "+string);
            }
            sd.add("Total products:"+bill.size()+" Discount 10% "+tprce/10+" Total price:"+disprc+"\n");
        }
        else if(tprce<=1000){
            wallet+=tprce;
            for (String string : bill) {
                sd.add("Prodect selled "+string);
            }
            sd.add("Total products:"+bill.size()+" Total price:"+tprce);
        }
    }
    void cart(){
        disprc=0;
        int sno=0;
            for(int a=1;a<=bill.size();a++){  
                System.out.print("S.No "+a+" " );System.out.println(bill.get(sno++));
            }
            System.out.println("Total products:"+bill.size());
            System.out.println("Total price:"+tprce);
        if (tprce>1000) {
            disprc=(tprce-(tprce/10));
            System.out.println("Discount 10% "+tprce/10);
            System.out.println("Total price:"+disprc);
        }
    }
    void sd(){
        for (String i : sd) {
            System.out.println(i);   
        }
    }
    void LowStocks(){
        int o=0;
        for (int i : prd.keySet()) {
            if(prd.get(i).product_quantity<10){
                System.out.println(prd.get(i).product_name);System.out.print(" "+prd.get(i).product_quantity);
            }
            else{
                o++;
            }
            if(o==prd.size()){
                System.out.println("No Products in low Stock.");
            }
        }
    }
    public static void main(String[] args){
        Menu m=new Menu();
        Scanner muf=new Scanner(System.in);
        while (true){
            System.out.println("\n Welcome to Java grocery shop.");
            System.out.println("1.Add new Products.");
            System.out.println("2.Remove Product.");
            System.out.println("3.Modify stocks.");
            System.out.println("4.Modify Product details.");
            System.out.println("5.Viwe Products.");
            System.out.println("6.Viwe all products.");
            System.out.println("7.Genarate Bill.");
            System.out.println("8.Wallet");
            System.out.println("9.Low Stocks");
            System.out.println("0.Exit.");
            System.out.println("Enter choice.");
            int choice=muf.nextInt();
            if (choice==1){
                while (true) {
                    System.out.println("\nEnter 0 for Exit.");
                    System.out.println("New Product ID:");
                    int prdid=muf.nextInt();
                    if(prdid==0)break;
                    else if(m.prd.containsKey(prdid)){
                        System.out.println("Stock ready exsits.");
                    }
                    else{
                        muf.nextLine();
                        System.out.println("New Product Name.");
                        String prdnm=muf.nextLine();
                        System.out.println("New Product Price.");
                        Double prdprc=muf.nextDouble();
                        System.out.println("New Product Quantity.");
                        Double prdqnt=muf.nextDouble();
                        m.addpr(prdid,prdnm,prdqnt,prdprc);
                        m.sd.add("Product Added ID:"+prdid+" PRODUCT:"+prdnm+" PRICE:"+prdprc+" QNT:"+prdqnt);
                    }
                }
            }
            else if (choice==2) {
                while(true){
                    System.out.println("Enter 0 for Exit.");
                    System.out.println("Enter prduct ID.");
                    int prid=muf.nextInt();
                    if(m.prd.containsKey(prid)){
                        m.sd.add("Product Removed ID:"+prid+" PRODUCT:"+m.prd.get(prid).product_name+" PRICE:"+m.prd.get(prid).product_price+" QNT:"+m.prd.get(prid).product_quantity);
                        m.tsv-=m.prd.get(prid).product_price*m.prd.get(prid).product_quantity;
                        m.prd.remove(prid);
                        System.out.println("Product Removed from stock.");                
                    }
                    else if(prid==0)break;
                    else{
                        System.out.println("No stock found!");
                    }
                }
            }
            else if(choice==3) {
                while (true) {
                System.out.println("\nEnter 0 for Exit.");
                System.out.println("1.Add stocks.");
                System.out.println("2.Remove stocks.");
                int choice4=muf.nextInt();
                    if (choice4==0) break;
                    else if(choice4==1){
                        System.out.println("Enter Product ID.");
                        int prdid=muf.nextInt();
                        if(m.prd.containsKey(prdid)){
                            System.out.println("Enter product quantity");
                            double prq=muf.nextDouble();
                            m.sd.add("Stock Added ID:"+prdid+" PRODUCT:"+m.prd.get(prdid).product_name+" QNT:"+m.prd.get(prdid).product_quantity +" + "+prq);
                            m.prd.get(prdid).product_quantity+=prq;
                            m.tsv+=m.prd.get(prdid).product_price*prq;
                            System.out.println("Stocks Added.");
                        }
                        else {
                        System.out.println("No product found!");
                        }
                    }
                    else if(choice4==2){
                    System.out.println("Enter Product ID.");
                    int prdid=muf.nextInt();
                        if(m.prd.containsKey(prdid)){
                            System.out.println("Enter product quantity");
                            double prq=muf.nextDouble();
                            m.sd.add("Stock Removed ID:"+prdid+" PRODUCT:"+m.prd.get(prdid).product_name+" QNT:"+m.prd.get(prdid).product_quantity +" - "+prq);
                            m.prd.get(prdid).product_quantity-=prq;
                            m.tsv-=m.prd.get(prdid).product_price*prq;
                            System.out.println("Stocks Removed.");
                        }
                        else {
                            System.out.println("No product found!");
                        }
                    }
                }
            } 
            else if (choice==4) {
                System.out.println("\nEnter 0 for Exit.");
                System.out.println("Enter Product ID.");
                int prid=muf.nextInt();
                if(m.prd.containsKey(prid)){
                    System.out.println("1.Product Name.");
                    System.out.println("2.Product Price.");
                    int choice3=muf.nextInt();
                    if(choice3==1){
                        System.out.println("Enter product Name.");
                        muf.nextLine();
                        String prdname=muf.nextLine();
                        m.sd.add("ID:"+prid+" NAME:"+ m.prd.get(prid).product_name+" PRC:"+m.prd.get(prid).product_price+" Stock name modified as  "+prdname);
                        m.prd.get(prid).product_name=prdname;
                        System.out.println("Product Name modified as "+prdname);
                    }
                    else if (choice3==2){
                        System.out.println("Enter Product price");
                        double prdprc=muf.nextDouble();
                        m.tsv-=(m.prd.get(prid).product_price*m.prd.get(prid).product_quantity);
                        m.sd.add("ID:"+prid+" NAME:"+ m.prd.get(prid).product_name+" PRC:"+m.prd.get(prid).product_price+" Stock price modified as "+prdprc);
                        m.prd.get(prid).product_price=prdprc;
                        m.tsv+=(m.prd.get(prid).product_price*m.prd.get(prid).product_quantity);
                        System.out.println("Product price Modified as "+prdprc);
                    }
                }
                else{
                    System.out.println("Product not found");
                }
            }
            else if(choice==5){
                System.out.println("1.viwe Product by ID");
                System.out.println("2.Viwe Product by Name");
                int chic=muf.nextInt();
                if(chic==1){
                    while(true){
                        System.out.println("\nEnter 0 for Exit.");
                        System.out.println("Enter Product ID.");
                        int prdid=muf.nextInt();
                        if(prdid==0)break;
                        m.viwe_productID(prdid);
                    }
                }
                if (chic==2) {
                    muf.nextLine();
                    while(true){
                        System.out.println("\nEnter 0 for exit");
                        System.out.println("Enter Product Name.");
                        String prdnm=muf.nextLine();
                        if(prdnm.equals("0"))break;
                        m.viwe_productName(prdnm);
                    }
                }
                else{
                    System.out.println("Invalid choice.");
                }
            }
            else if(choice==6){
                m.viwe_all_product();
            }
            else if(choice==7){
                while (true){
                    System.out.println("Enter 0 for Exit.");
                    System.out.println("1.Add products.");
                    System.out.println("2.Remove product.");
                    System.out.println("3.Cart.");
                    System.out.println("4.Payment.");
                    int choice2=muf.nextInt();
                    if(choice2==0)break;
                    else if (choice2==1){
                        while(true){
                            System.out.println("Enter 0 for Exit.");
                            System.out.println("1.Add product by ID");
                            System.out.println("2.Add product by Name.");
                            int choice3=muf.nextInt();
                            if(choice3==1){
                                while(true){
                                    System.out.println("Enter 0 for exit");
                                    System.out.println("Enter Product ID.");
                                    int prdid=muf.nextInt();
                                    if(prdid==0) break;
                                    else if(m.prd.containsKey(prdid)){
                                        System.out.println("Enter quantity");
                                        double quantity=muf.nextDouble();
                                        if(m.prd.get(prdid).product_quantity >= quantity){
                                            m.bill_product(prdid,quantity);
                                            System.out.println("             JAVA GROCERY SHOP ");
                                            m.cart();
                                        }
                                        else{
                                            System.out.println("You selecting obove of stock!");
                                            System.out.println(" stock:"+m.prd.get(prdid).product_quantity);
                                        } 
                                    }
                                    else{
                                        System.out.println("No product found!");
                                    }
                                }
                            }
                            else if(choice3==2){
                                muf.nextLine();
                                while(true){
                                    int a=0;
                                    System.out.println("Enter Product Name.");
                                    String name=muf.nextLine();
                                    if (name.equals("0"))break;
                                    for (int i : m.prd.keySet()){
                                        if(m.prd.get(i).product_name.equals(name)){
                                            System.out.println("Enter product quantity.");
                                            double quantity=muf.nextDouble();
                                            muf.nextLine();
                                            if(m.prd.get(i).product_quantity >= quantity){
                                                m.bill_product(i,quantity) ;
                                                System.out.println("             JAVA GROCERY SHOP ");
                                                m.cart();break;
                                            }
                                            else{
                                                System.out.println("You selecting obove of stock!");
                                                System.out.println(" stock:"+m.prd.get(i).product_quantity);
                                            } 
                                        }
                                        else  {
                                            a++;
                                        }
                                        if (a==m.prd.size()) {
                                            System.out.println("No product found!");
                                        }
                                    }
                                }
                            }
                            else if(choice3==0) break;
                            else {
                                System.out.println("Invalid choice.");
                            }
                        }
                    }
                    else if(choice2==2){
                        System.out.println("Enter product S.NO.");
                        int sno=muf.nextInt();
                        m.item-=1;
                        try{
                            m.prd.get(m.carInt.get(sno-1)).product_quantity+=m.carDbl.get(sno-1);
                            m.tprce-=(m.prd.get(m.carInt.get(sno-1)).product_price*m.carDbl.get(sno-1));
                            m.bill.remove(sno-1);
                            m.carInt.remove(sno-1);
                            m.carDbl.remove(sno-1);
                            System.out.println("Product Removed from your cart"); 
                        }
                        catch(IndexOutOfBoundsException e){
                            System.out.println("enter correct S.NO.");
                        }                                                          
                    }
                    else if(choice2==3){
                        System.out.println("             JAVA GROCERY SHOP ");
                        m.cart();              
                    }
                    else if(choice2==4){
                        while(true){
                            String pay=muf.nextLine();
                            if(pay.equals("paid")){
                                m.AddCartInSd();
                                m.tsv-=m.tprce;
                                m.item=0;
                                m.bill.clear();
                                m.tprce=0;
                                m.disprc=0;
                                System.out.println("Thank you come again.");
                                break;
                            }
                            else if(pay.equals("no paid")) {
                                m.item=0;
                                int j=0;
                                m.tprce=0;
                                m.disprc=0;
                                m.bill.clear();
                                for (int i : m.carInt) {
                                    m.prd.get(i).product_quantity+=m.carDbl.get(j);j++;
                                }
                                m.carInt.clear();m.carDbl.clear();
                                System.out.println("thank come again.");
                                break;
                            }
                            else{
                                System.out.println("Enter paid or no paid!");
                            }
                        }
                    }
                }
            }
            else if (choice==8){
                while(true){
                    System.out.println("\nEnter 0 Exit");
                    System.out.println("1.Payment");
                    System.out.println("2.Sale details");
                    System.out.println("Totall Amount in Wallet."+m.wallet);
                    int choice2=muf.nextInt();
                    if (choice2==0) break;
                    else if(choice2==1){
                        System.out.println("1.Pay Money.");
                        System.out.println("2.Add Money.");
                        int choice3=muf.nextInt();
                        if(choice3==1){
                            System.out.println("Enter Amount.");
                            double amount=muf.nextInt();
                            if(amount<=m.wallet){
                                m.wallet-=amount;
                                System.out.println("Payment successfull!");
                            }
                            else{
                                System.out.println("You selecting above from wallet!");
                            }
                        }
                        else if(choice3==2){
                            System.out.println("Enter Amount.");
                            double amount=muf.nextInt();
                            m.wallet+=amount;
                            System.out.println("Money Added successfull!");
                        }
                    }
                    else if(choice2==2){
                        for (String i : m.sd) {
                            System.out.println(i);
                        }
                    }
                }
            }
            else if (choice==9) {
                m.LowStocks();
            }
            else if (choice==0) {
                break;
            }
            else {
                System.out.println("Invalid choice.");
            }
        }
    }  
}           