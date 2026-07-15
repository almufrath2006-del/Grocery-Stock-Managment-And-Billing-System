# Grocery Stock Management System

## Overview
A console-based Grocery Stock Management System developed using 
Core Java with JDBC and MySQL database integration. This project 
simulates a real-world grocery store management system where store 
managers can manage inventory, generate bills, and track all 
transactions in real time through a MySQL database.

## Features
- Add new products with ID, name, price, and quantity
- Remove products from inventory with sales log entry
- Add and remove stock quantity for existing products
- Modify product name and price details
- Low stock alert for products with quantity below 10
- View individual product details with stock value
- View all products with total stock value and product count
- Cart-based billing system — add products by ID or name
- Remove products from cart before payment
- Wallet management to track total store revenue
- Sales history log for every transaction and operation
- Payment management — send and receive money with wallet

## Technologies Used
- Core Java
- JDBC — Java Database Connectivity
- MySQL Database
- Java Collections — ArrayList

## Database Tables
- product — stores product ID, name, price, quantity
- sales_details — logs every transaction and operation
- wallet — tracks store revenue and payments

## How to Run
1. Clone the repository
2. Create MySQL database named 'grocery'
3. Create product, sales_details, and wallet tables
4. Add MySQL connector JAR to lib folder
5. Update DB credentials in code
6. Compile and run Grocery.java
