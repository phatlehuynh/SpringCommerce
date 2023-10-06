import React from 'react'
import ReactDOM from 'react-dom/client'
import App from './App.jsx'
import Contact from './pages/Contact.jsx'
import LoginForm from './pages/LoginForm.jsx'
// import Product from './pages/Product.jsx'
import ProductView from './pages/ProductView.jsx'
import CategoryFilter from './pages/CategoryFilter.jsx'

import './index.css'
import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";
import { Provider } from 'react-redux'
import RegisterForm from './pages/RegisterForm.jsx'
import { PersistGate } from 'redux-persist/integration/react'
import {persistor, store} from './redux/store.js'

const router = createBrowserRouter([
  {
    path: "/",
    element:  <Provider store={store}> 
                <PersistGate loading={null} persistor={persistor}>
                  <App/>
                </PersistGate>
              </Provider>,
  },
  {
    path: "contact",
    element: <Contact/>,
  },
  {
    path: "login",
    element:  <Provider store={store}> 
                <LoginForm/> 
              </Provider> ,
  },
  {
    path: "product/:productId",
    element: <ProductView/>,
  },
  {
    path: "category",
    element: <CategoryFilter/>,
  },

  {
    path: "register",
    element: <Provider store={store}><RegisterForm/></Provider>,
  },
  
]);

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <RouterProvider router={router} />  
  </React.StrictMode>,
)
