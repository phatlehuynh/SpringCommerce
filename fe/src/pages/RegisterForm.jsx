import { useFormik } from "formik"
import { useState } from "react"

import * as Yup from 'yup'
import { registerUser } from "../api/register"
import { useDispatch } from "react-redux"
import { useNavigate } from "react-router-dom"

export default function RegisterForm(){

    const [gender, setGender] = useState('')
    const dispatch = useDispatch()
    const navigate = useNavigate()
    
    const handleRadioCheck = (event) => {
        setGender(event.target.value);
    }

    const handleRegister = (values) => {
        const user = {
            username: values.email,
            password: values.password
        }

        registerUser(user, dispatch, navigate)
    }

    // Xử lí form bằng formik
    const formik = useFormik({
        initialValues:{
            email: '',
            firstName: '',
            lastName: '',
            password: '',
            confirmedPassword: '',
            birthday: '',
        },

        validationSchema: Yup.object({
            email: Yup.string().required('Required').matches(/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/, 'Please enter valid email address'),
            firstName: Yup.string().required('Required'),
            lastName:  Yup.string().required('Required'),
            password:  Yup.string().required('Required').min(6, 'Must be 6 characters or more'),
            // confirmedPassword:  Yup.string().required('Required').oneOf([Yup.ref('password'), null], 'Password is not correct'),
            birthday:  Yup.string().required('Required'),
            // gender:  Yup.string().required('Required'),
        }),

        onSubmit: (values) => {
            values.gender = gender
            handleRegister(values)
            console.log(values)
        },

    })



    return (
        <div className="bg-slate-500 flex flex-col items-center absolute w-full h-screen">

        {/* Đăng kí bằng gg hoặc fb */}
        <header className="mt-4">
            <h1 className="text-center font-semibold text-lg">REGISTER TO COME UP WITH US</h1>

            <div className="mt-4">
                <button className="m-4 bg-slate-300 rounded-md h-9 p-2">Sign up with Google</button>
                <button className="m-4 bg-blue-500 rounded-md h-9 p-2">Sign up with FB</button>
            </div>
        </header>
        {/* END Đăng kí bằng gg hoặc fb */}


        {/* Form đăng kí chính */}
        <main className="mt-5">

            <form action="" className="flex flex-col justify-center items-center" onSubmit={formik.handleSubmit}>

                <div className="s4n6-container-field">

                    {formik.errors.email && (
                        <p className="s4n6-error-msg">{formik.errors.email}</p>
                    )}
                   <input 
                    className="s4n6-field-form" 
                    type="text" 
                    name="email" 
                    id="email" 
                    onChange={formik.handleChange}
                    value={formik.values.email}
                    placeholder="Email address"
                   />
                   

                </div>

                <div className="s4n6-container-field">
                    {formik.errors.password && (
                        <p className="s4n6-error-msg">{formik.errors.password}</p>
                    )}
                   <input className="s4n6-field-form"
                    type="text" 
                    name="password" 
                    id="password" 
                    placeholder="Password"
                    onChange={formik.handleChange}
                    value={formik.values.password}
                    />
                </div>

                <div className="s4n6-container-field">
                    {formik.errors.firstName && (
                        <p className="s4n6-error-msg">{formik.errors.firstName}</p>
                    )}
                    <input 
                    className="s4n6-field-form" 
                    type="text" 
                    name="firstName" 
                    id="firstName" 
                    placeholder="First Name"
                    onChange={formik.handleChange}
                    value={formik.values.firstName}
                    />
                </div>

                <div className="s4n6-container-field">
                    {formik.errors.lastName && (
                        <p className="s4n6-error-msg">{formik.errors.lastName}</p>
                    )}
                    <input 
                    className="s4n6-field-form" 
                    type="text" 
                    name="lastName" 
                    id="lastName" 
                    placeholder="Last Name"
                    value={formik.values.lastName}
                    onChange={formik.handleChange}
                    />
                </div>

                <div className="s4n6-container-field">
                    {formik.errors.birthday && (
                        <p className="s4n6-error-msg">{formik.errors.birthday}</p>
                    )}
                    <input 
                        className="s4n6-field-form" 
                        type="date"
                        name="birthday" 
                        id="birthday" 
                        placeholder="Date of birth"
                        value={formik.values.birthday}
                        onChange={formik.handleChange}
                    />

                </div>


                <div className="s4n6-container-field flex items-center justify-center">
                    <h3 className="mr-5">Gender</h3>
                    <div className="flex justify-center items-center m-4">
                        <input 
                            className="h-6 w-6 mr-2 cursor-pointer" 
                            type="radio" 
                            id="gender-male" 
                            name="gender" 
                            value='male'
                            onChange={handleRadioCheck}
                            
                        />
                        <label htmlFor="gender-male">Male</label>
                    </div>

                    <div className="flex justify-center items-center m-4">
                        <input 
                            className="h-6 w-6 mr-2 cursor-pointer" 
                            type="radio" 
                            id="gender-female" 
                            name="gender" 
                            value="female"
                            onChange={handleRadioCheck}
                        />
                        <label htmlFor="gender-female">Female</label>
                    </div>

                    

                </div>

                {/* Đồng ý điều khoản */}
                <div className="flex mt-8">
                    <input className="mr-2" type="checkbox" name="" id="btnAgreeTerms"/>
                    <label htmlFor="btnAgreeTerms">
                        <h3>By singing up, you are creating a Shop account, and you agree to Shops
                           <a className="underline" href="">Terms of Use</a> and <a className="underline" href="">Privacy Policy</a>
                        </h3>
                    </label>
                   
                </div>
                 {/* END Đồng ý điều khoản */}

                <button className="m-5 bg-slate-300 rounded-md h-10 w-1/3 hover:opacity-75" type="submit">Create an account</button>

            </form>
            
        </main>

        {/* END Form đăng kí chính */}


        <footer className="mt-3">
            <h2>Already have an account? <a className="underline" href="">Login here</a> </h2>
        </footer>

    </div>)
}