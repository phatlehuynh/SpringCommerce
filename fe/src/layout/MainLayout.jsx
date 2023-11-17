import React from 'react'
import Navbar from '../components/Navbar'
import Footer from '../components/Footer'
import Banner from '../components/Banner'

function MainLayout({children}) {
  return (
    <div>
        <Banner/>
        <Navbar/>
        <div>    
            {children}
        </div>
        <Footer/>
    </div>
  )
}

export default MainLayout