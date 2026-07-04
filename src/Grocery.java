import java.sql.*;
import java.util.*;
public class Grocery{
    void Add(int id,String nm,double pr,double qn){
        try{
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery","root","1234");
            Statement st=con.createStatement();
            int rs=st.executeUpdate("insert into product value("+id+",'"+nm+"',"+pr+","+qn+");");
            System.out.println("Product Added");
            int r=st.executeUpdate("insert into sales_details value('Product Added ID:"+id+" Name:"+nm+" Price:"+pr+" Quantity:"+qn+"')");
        }
        catch (Exception e) {
            System.out.println("Product already Exist");
        }
    }
    void delete(int rmid){
        try{
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery","root","1234");
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select * from product where id="+rmid+";");
            rs.next();
            int rs2=st.executeUpdate("insert into sales_details value('Product Removed ID:"+rs.getInt(1)+" Product:"+rs.getString(2)+" Price:"+rs.getDouble(3)+" Quanttity:"+rs.getDouble(4)+"');");
            int rs1=st.executeUpdate("delete from product where id="+rmid);
            System.out.println("product Removed");
        }
        catch(Exception e){
            System.out.println("No product Found");
        }
    }
    void Modify(int prid,double Add,double Rem){
         try{
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery","root","1234");
            Statement st=con.createStatement();
            if(Add!=0){
                int r=st.executeUpdate("update product set quantity=quantity+"+Add+" where id="+prid+";");
                if(r!=0){
                System.out.println("Stock Added");
                int t=st.executeUpdate("insert into sales_details value('Product ID:"+prid+" Stock Added:"+Add+"');");
                }
                else{
                    System.out.println("Product Not Found And Stock not Modified");
                }
            }
            else{
                ResultSet rs=st.executeQuery("select quantity from product where id="+prid+";");
                rs.next();
                if(rs.getDouble(1)<Rem){
                    System.out.println("Selected Above stock");
                }
                else{
                    int r=st.executeUpdate("update product set quantity=quantity-"+Rem+" where id="+prid+";");
                    System.out.println("Stock Removed");
                    int t=st.executeUpdate("insert into sales_details value('Product ID:"+prid+" Stock Removed:"+Rem+"');");
                }
            }
        }
        catch (Exception e) {
            System.out.println("Prodect Not found");
        }
    }
    void ModifyProductDtails(int prid,String Name,double Price){
        try{
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery","root","1234");
            Statement st=con.createStatement();
            if(!Name.equals("muf")){
                int r=st.executeUpdate("update product set name='"+Name+"' where id="+prid+";");
                if (r!=0) { 
                    System.out.println("Stock Name changd as "+Name);
                    int t=st.executeUpdate("insert into sales_details value('Stock Name Changed into "+Name+"');");
                }
                else{
                    System.out.println("Product Not Found And Details not Modified");
                }
            }
            else{
                int r=st.executeUpdate("update product set price="+Price+" where id="+prid+";");
                if (r!=0) {
                    System.out.println("Stock Price Changed as "+Price);
                    int t=st.executeUpdate("insert into sales_details value('Stock Price Changed into "+Price+"');");
                }
                else{
                    System.out.println("Prodect Not found");
                }
            }
        }
        catch (Exception e) {}
    }
    void ViewAll(){
        try{
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery","root","1234");
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select * from product");
            int StockValue=0;
            int i=0;
            while (rs.next()) {
                System.out.println("Product ID:"+rs.getInt(1)+" Product:"+rs.getString(2)+" Price:"+rs.getDouble(3)+" Quanttity:"+rs.getDouble(4));
                StockValue+=rs.getDouble(3)*rs.getDouble(4);i++;
            }
            System.out.println("Total Stock Value:"+StockValue);
            System.out.println("Total Products:"+i);
        }
        catch(Exception e){}
    }
    void ViewProduct(int selid){
        try{
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery","root","1234");
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select * from product where id="+selid+";");
            rs.next();
            System.out.println("Product ID:"+rs.getInt(1)+" Product:"+rs.getString(2)+" Price:"+rs.getDouble(3)+" Quanttity:"+rs.getDouble(4));
            System.out.println("Product Stock value:"+rs.getDouble(3)*rs.getDouble(4));
        }
        catch(Exception e){
            System.out.println("Product Not Found");
        }
    }
    List<String>cart=new ArrayList<>();
    void cart(){
        int ttlpr=0;
        int i=1;
        for (String str:cart) {
            System.out.println("Sno:"+i++ +" " +str); ttlpr++;  
        }
        System.out.println("total Products:"+ttlpr);
        System.out.println("Total Price:"+total);
    }
    int total=0;
    int wallet=0;
    List<Integer>carInt=new ArrayList<>();
    List<Double>carDbl=new ArrayList<>();
    void Bill(int id,double qn,String name,int remId,double remDbl){
        try{
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery","root","1234");
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select quantity from product where id="+id+";");
            if(name.equals("muf")&&remId==0){
                try{
                    rs.next();
                    if(rs.getInt(1)>=qn){
                        int r=st.executeUpdate("update product set quantity=quantity-"+qn+" where id="+id+";");
                        ResultSet price=st.executeQuery("select price from product where id="+id+";");
                        price.next();
                        double prc=price.getDouble(1);
                        total+=prc*qn;
                        ResultSet product=st.executeQuery("select name from product where id="+id+";");
                        product.next();
                        String prd=product.getString(1);
                        cart.add("product:"+prd+" price:"+prc+" quantity:"+qn+" amt:"+prc*qn);
                        System.out.println("product added to cart.");
                    }
                    else{
                        System.out.println("above stock");
                    }
                }
                catch(Exception e){
                    System.out.println("product not found");
                }
            }
            else if(!name.equals("muf")){
             try{
                    ResultSet rs2=st.executeQuery("select quantity from product where name='"+name+"';");
                    rs2.next();
                    if(rs2.getInt(1)>=qn){
                        int r=st.executeUpdate("update product set quantity=quantity-"+qn+" where name='"+name+"';");
                        ResultSet price=st.executeQuery("select price from product where name='"+name+"';");
                        price.next();
                        double prc=price.getDouble(1);
                        total+=prc*qn;
                        ResultSet product=st.executeQuery("select name from product where name='"+name+"';");
                        product.next();
                        String prd=product.getString(1);
                        cart.add("product:"+prd+" price:"+prc+" quantity:"+qn+" amt:"+prc*qn);
                        System.out.println("product added to cart.");
                    }
                    else{
                        System.out.println("above stock");
                    }
                }
                catch(Exception e){
                    System.out.println("product not found");
                }   
            }
            else if(remDbl!=0){
                int i=st.executeUpdate("update product set quantity=quantity+"+remDbl+" where id="+remId+";");
                ResultSet rs2=st.executeQuery("select price from product where id="+remId+";");
                rs2.next();
                total-=rs2.getDouble(1)*remDbl;
                System.out.println("Product Removed from cart");
            }
        }
        catch(Exception e){}
    }
    void LowStock(){
        try{
            int o=0;
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery","root","1234");
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select * from product where quantity<10;");
            while (rs.next()){
                System.out.println("Product ID:"+rs.getInt(1)+" Product:"+rs.getString(2)+" Price:"+rs.getDouble(3)+" Quanttity:"+rs.getDouble(4));o++;            
            }
            System.out.println(o+" Products in Low Stock");
        }
        catch(Exception e){}
    }
    void View_Sales_Details(){
        try{
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery","root","1234");
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select * from sales_details;");
            while (rs.next()) {
                System.out.println(rs.getString(1));            
            }
        }
        catch(Exception e){}
    }
    void payment(int pay,int rec,int amt){
        try{
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery","root","1234");
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("select * from wallet;");
            rs.next();
            if(pay!=0){
                if(rs.getInt(1)>pay){
                    int r=st.executeUpdate("update wallet set wallet=wallet-"+pay+";");
                    int k=st.executeUpdate("insert into sales_details value('Amount Paid:"+pay+"');");
                    System.out.println("Amount Paid");
                }
                else{
                    System.out.println("Insuffiient Blance");  
                }
            }
            else if(amt!=0){
                ResultSet rs2=st.executeQuery("select * from wallet");rs2.next();
                System.out.println("Amount:"+rs2.getDouble(1));
            }
            else{
                int r=st.executeUpdate("update wallet set wallet=wallet+"+rec+";");
                int k=st.executeUpdate("insert into sales_details value('Amount Recived:"+rec+"');");
                System.out.println("Amount Recived");
            }
        }
        catch(Exception e){}
    }
    void sellPay(String result){
        try{
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/grocery","root","1234");
            Statement st=con.createStatement();
            if(result.equals("paid")){
                int r=st.executeUpdate("update wallet set wallet=wallet+"+total+";");
                for (String i : cart) {
                    int s=st.executeUpdate("insert into sales_details value('Salled "+i+"');");
                }
                int s=st.executeUpdate("insert into sales_details value('Total Products:"+cart.size()+" Total Price:"+total+"');");
                total=0;
                carDbl.clear();carInt.clear();cart.clear();
                System.out.println("Thank You Come Again");
            }
            else if(result.equals("no paid")){
                int j=0;
                total=0;
                cart.clear();
                for (int i : carInt) {
                    int k=st.executeUpdate("update product set quantity=quantity+"+carDbl.get(j)+" where id="+i+";");j++;
                }
                carInt.clear();carDbl.clear();
                System.out.println("Thank Come Again.");
            }
        }
        catch(Exception e){}
    }
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        Grocery obj=new Grocery();
        while (true) {
            System.out.println("\nWellcome to JavaMart");
            System.out.println("1.Add New Product\n2.Remove Product\n3.Modify Stocks\n4.Modify Product Details\n5.View Products");
            System.out.println("6.View All Products\n7.Genarate Bill\n8.Details\n9.Low Stocks\n0.Exit");
            int choic=sc.nextInt();
            if (choic==1) {
                while(true){
                    System.out.println("Enter product id:");
                    int id=sc.nextInt();
                    if(id!=0){
                        sc.nextLine();
                        System.out.println("Enter product name:");
                        String nm=sc.nextLine();
                        System.out.println("Enter product price:");
                        double pr=sc.nextDouble();
                        System.out.println("Enter Product quantity:");
                        double qn=sc.nextDouble();
                        obj.Add(id,nm,pr,qn);
                    }
                    else break;
                }
            }
            else if(choic==2){
                System.out.println("Enter product ID:");
                int id=sc.nextInt();
                obj.delete(id);
            }
            else if(choic==3){
                while(true){
                    System.out.println("Enter Poduct ID:");
                    int prid=sc.nextInt();
                    if (prid==0)break;
                    System.out.println("1.Add Stock\n2.Remove Stocks");
                    int chi=sc.nextInt();
                    double Add=0;double Rem=0;
                    if(chi==1){
                        System.out.println("Enter Add Quantity:");
                        Add=sc.nextInt();
                    }
                    else if(chi==2){
                        System.out.println("Enter Remove Quantity:");
                        Rem=sc.nextInt();
                    }
                    obj.Modify(prid,Add,Rem);
                }
            }
            else if(choic==4){
                while (true) {
                    System.out.println("Enter product ID:");
                    int id=sc.nextInt();
                    if(id==0)break;
                    System.out.println("1.Stock Name\n2.Stocks Price");
                    int chi=sc.nextInt();
                    sc.nextLine();
                    String Name="muf";double price=0;
                    if(chi==1){
                        System.out.println("Enter New Name:");
                        Name=sc.nextLine();
                    }
                    else if(chi==2){
                        System.out.println("Enter New Price:");
                        price=sc.nextInt();
                    }
                    obj.ModifyProductDtails(id, Name, price);
                }
            }
            else if(choic==5){
                System.out.println("Product ID:");
                int selid=sc.nextInt();
                if(selid==0)break;
                obj.ViewProduct(selid);
            }
            else if(choic==6){
                obj.ViewAll();
            }
            else if (choic==7) {
                while(true){
                    System.out.println("\n1.Add Product\n2.Remove\n3.Cart\n4.Payment");
                    int chi=sc.nextInt();
                    String name="muf";
                    if(chi==1){
                        while (true) {
                            System.out.println("\n1.By ID.\n2.By Name.");
                            int chi2=sc.nextInt();
                            if(chi2==1){
                                System.out.println("Enter Product ID:");
                                int id=sc.nextInt();
                                if(id==0)break;
                                System.out.println("Enter Quantity:");
                                Double qn=sc.nextDouble();
                                obj.Bill(id,qn,name,0,0);
                                obj.carInt.add(id);
                                obj.carDbl.add(qn);
                            }
                            else if(chi2==2){
                                while (true) {
                                    sc.nextLine();
                                    System.out.println("Enter Product Name:");
                                    name=sc.nextLine();
                                    if(name.equals("0"))break;
                                    System.out.println("Enter Quantity:");
                                    Double qn=sc.nextDouble();
                                    obj.Bill(0,qn,name,0,0);
                                    obj.carDbl.add(qn);
                                }
                            }
                            else if(chi2==0)break;
                        }
                    }
                    else if(chi==2){
                        System.out.println("Enter SNo:");
                        int sno=sc.nextInt();
                        try{
                        int id=obj.carInt.get(sno-1);
                        double qn=obj.carDbl.get(sno-1);
                        obj.Bill(0,0,"muf",id,qn);
                        obj.cart.remove(sno-1);
                        }
                        catch(Exception e){
                            System.out.println("Select Correct Product SNo.");
                        }
                    }
                    else if(chi==3){
                    obj.cart();
                    }
                    else if(chi==4){
                        sc.nextLine();
                        String result=sc.nextLine();
                        obj.sellPay(result);
                    }
                    else if(chi==0)break;
                }
            }
            else if(choic==8){
                System.err.println("1.Payment\n2.Sales Details\n3.Wallet");
                int cho=sc.nextInt();
                if(cho==1){
                    System.out.println("1.Pay Money\n2.Recieve Money");
                    int cho2=sc.nextInt();
                    int pay=0;int rec=0;
                    if(cho2==1){
                        System.out.println("Enter Payment Amount");
                        pay=sc.nextInt();
                    }
                    else if(cho2==2){
                        System.out.println("Enter Recieved Money");
                        rec=sc.nextInt();
                    }
                    obj.payment(pay,rec,0);
                }
                else if(cho==2){
                    obj.View_Sales_Details();
                }
                else if(cho==3){
                    obj.payment(0, 0,1);
                }
            }
            else if(choic==9){
                obj.LowStock();
            }
            else if(choic==0)break;  
            else{
                System.out.println("Invalid Choice");
            }
        }
    }
}