import Navbar from '../src/components/Navbar';
import Product from '../src/components/Product';
import ProductPage from '../src/components/ProductPage';
import Hero from './/components/Hero';
import HeadLineCart from './/components/HeadLineCard';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Category from './components/Category';
import CategoryProduct from './components/CategoryProduct';
import Login from './components/Login';
import Register from './components/Register';
import Link from './components/Link';
import ProductForm from './components/ProductForm';
import ProductAdmin from './components/ProductAdmin';
import ProductUpdateForm from './components/ProductUpdateForm';
import ShoppingCart from './components/ShoppingCart';
import Search from './components/Search';
import Filter from './components/Filter';
import Payment from './components/Payment';
import BillList from './components/BillList';
import LinkChangePass from './components/LinkChangePass';
import ChangePassForm from './components/ChangePassForm';
import Footer from './components/Footer';
import React, { useState } from 'react';




function App() {
  const [categoryId, setCategoryId] = useState('');
  const [keyword, setKeyword] = useState('');

  return (
    <div className="App">
      <BrowserRouter>
        <Navbar></Navbar>
        <Routes>
                    
          <Route path="/login" element ={<Login></Login>}></Route>
          <Route path="/signup/:email" element ={<Register></Register>}></Route>
          <Route path="/changepass/:token" element ={<ChangePassForm></ChangePassForm>}></Route>
          <Route path="/link" element ={<Link></Link>}></Route>
          <Route path="/linkChangePass" element ={<LinkChangePass></LinkChangePass>}></Route>
          <Route path="/Product/Form" element ={<ProductForm></ProductForm>}></Route>
          <Route path="/ProductUpdateForm/:id" element ={<ProductUpdateForm></ProductUpdateForm>}></Route>
          <Route path="/Product/Admin" element ={<ProductAdmin></ProductAdmin>}></Route>
          <Route path="/Cart" element ={<ShoppingCart></ShoppingCart>}></Route>
          <Route path="/payment" element={<Payment/>} />
          <Route path="/bill" element ={<BillList></BillList>}></Route>
          <Route
            path="/"
            element={
              <div>
                {/* Content specific to the home route */}
                <Hero></Hero>
                <HeadLineCart></HeadLineCart>
                <Category setCategoryId={setCategoryId}></Category>
                <div className="content">
                  <Product></Product>
                </div>
          
              </div>
            }
          />
           <Route
            path="/Search/:keyword"
            element={
              <div>
                {/* Content specific to the home route */}
                <Hero></Hero>
                <Category
                setCategoryId={setCategoryId}></Category>
                <div className="content">
                  <Search
                    categoryId={categoryId}
                    setCategoryId={setCategoryId}
                  ></Search>
                </div>
                
              </div>
            }
          />
        
          <Route
            path="/category/:id"
            element={
              <div>
                {/* Content specific to the home route */}
                <Hero></Hero>
                <HeadLineCart></HeadLineCart>
                <Category 
                categoryId={categoryId}
                setCategoryId={setCategoryId}></Category>
                <div className="content">
                  <CategoryProduct categoryId={categoryId}
                    setCategoryId={setCategoryId}></CategoryProduct>
                </div>
              </div>
            }
          />
          <Route path="/product/:id" element={<ProductPage />} />
        </Routes>
        <Footer></Footer>
      </BrowserRouter>
    </div>
  );
}

export default App;
