package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.Entity.Account;

public class AccountDao {
	String result="";
	
	public String insert(Connection con, String userid, String pass, String contact, String email, String city, String acholname, String actype, String atm )
	{
		try
		{
			String sql = "insert into account values(?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, userid);
			ps.setString(2, pass);
			ps.setString(3, contact);
			ps.setString(4, email);
			ps.setString(5, city);
			ps.setString(6, acholname);
			ps.setString(7, actype);
			ps.setString(8, atm);
			ps.setString(9, contact+"@OBA");
			ps.setString(10, "0");
			
			int i = ps.executeUpdate();
			if(i == 1)
			{
				result="inserted";
			}
			else
			{
				result="not inserted";
			}
		}
		catch (Exception e) {
			System.out.println("insert aacount"+e);
			result="not inserted";
		}
		finally {
			
			return result;
		}
	}
	
	public String login(Connection con, String uid, String upass)
	{
		try
		{
			String sql="select * from account";
			
			PreparedStatement ps = con.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery(sql);
			
			while(rs.next())
			{
				if(rs.getString(1).equals(uid) && rs.getString(2).equals(upass))
				{
					result="exists";
					break;
				}
				else
				{
					result="no exits";
				}
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
		finally {
			return result;
		}
	}
	
	public Account readAccount(Connection con, String id)
	{
		Account ac = new Account();
		try
		{
			String sql = "select * from account where userid=?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				ac.setUserid(rs.getString(1));
				ac.setPass(rs.getString(2));
				ac.setContact(rs.getString(3));
				ac.setEmail(rs.getString(4));
				ac.setCity(rs.getString(5));
				ac.setAchname(rs.getString(6));
				ac.setActype(rs.getString(7));
				ac.setAtm(rs.getString(8));
				ac.setAcno(rs.getString(9));
				ac.setBal(rs.getString(10));
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
		finally {
			return ac;
		}
	}
	
	
	public String depositMoney(Connection con, String uid, String am)
	{
		try
		{
			//reading old bal
			String bal = "";
			String sql1 = "select * from account where userid=?";
			
			PreparedStatement ps = con.prepareStatement(sql1);
			ps.setString(1, uid);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				bal= rs.getString("bal");
			}
			int b1 = Integer.parseInt(bal);
			int b2 = Integer.parseInt(am);
			
			int newBal = b1 + b2;
			
			String nb=String.valueOf(newBal);
			//update bal
			String sql2 = "update account set bal=? where userid=?";
			PreparedStatement ps1 = con.prepareStatement(sql2);
			ps1.setString(1, nb);
			ps1.setString(2, uid);
			
			int i1 = ps1.executeUpdate();
			
			if(i1==1)
			{
				result ="updated";
			}
			else
			{
				result ="failed";
			}
		}
		catch (Exception e) {
			result ="failed";
		}
		finally
		{
			return result;
		}
	}
	public String withdrawMoney(Connection con, String uid, String am)
	{
		try
		{
			//reading old bal
			String bal = "";
			String sql1 = "select * from account where userid=?";
			
			PreparedStatement ps = con.prepareStatement(sql1);
			ps.setString(1, uid);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				bal= rs.getString("bal");
			}
			int b1 = Integer.parseInt(bal);
			int b2 = Integer.parseInt(am);
			
			if(b1>b2)
			{
			int newBal = b1 - b2;
			
			String nb=String.valueOf(newBal);
			//update bal
			String sql2 = "update account set bal=? where userid=?";
			PreparedStatement ps1 = con.prepareStatement(sql2);
			ps1.setString(1, nb);
			ps1.setString(2, uid);
			
			int i1 = ps1.executeUpdate();
			
			if(i1==1)
			{
				result ="updated";
			}
			else
			{
				result ="failed";
			}
			}
			else {
				result="failed";
			}
		}
		catch (Exception e) {
			result ="failed";
		}
		finally
		{
			return result;
		}
	}
	
	public String moneyTransfer(Connection con,String uid,String am,String ahname)
	{
		try {
			//reading old bal
			String bal = "";
			String sql1 = "select * from account where userid=?";
			
			PreparedStatement ps = con.prepareStatement(sql1);
			ps.setString(1, uid);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				bal= rs.getString("bal");
			}
			int b1 = Integer.parseInt(bal);
			int b2 = Integer.parseInt(am);
			
			if(b1>=b2)
			{
				
				//Sending money by account number:
				
				String bal1 = "";
				String sql2 = "select * from account where acno=?";
				
				PreparedStatement ps1 = con.prepareStatement(sql2);
				ps1.setString(1, ahname);
				
				ResultSet rs1 = ps1.executeQuery();
				while(rs1.next())
				{
					bal1= rs1.getString("bal");
				}
				int b3 = Integer.parseInt(bal1);
				int b4 = Integer.parseInt(am);
				int newBal1 = b3+ b4;
				String nb1=String.valueOf(newBal1);
				
				String sql3="update account set bal=? where acno=?";
				PreparedStatement ps2 = con.prepareStatement(sql3);
				ps2.setString(1, nb1);
				ps2.setString(2, ahname);
				
				int i1 = ps2.executeUpdate();
				
				if(i1==1)
				{
					
					result ="updated";
				}
				else
				{
					result ="failed";
				}
				
				
				int newBal = b1 - b2;
					
				String nb=String.valueOf(newBal);
					
				//update bal
				String sql4 = "update account set bal=? where userid=?";
				PreparedStatement ps3 = con.prepareStatement(sql4);
				ps3.setString(1, nb);
				ps3.setString(2, uid);
					
				int i2 = ps3.executeUpdate();
					
				if(i2==1)
				{
					result ="updated";
				}
				else
				{
					result ="failed";
				}
			}	
			else {
				result="failed";
			}	
		}
		catch (Exception e) {
			result="failed";
		}
		finally
		{
			return result;
		}
	}
	
}



















