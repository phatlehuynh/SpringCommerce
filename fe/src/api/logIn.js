import axios from 'axios'
import { loginFailed, loginStart, loginSuccess } from '../redux/authSlice'

export const loginUser = async(user, dispatch, navigate) => {
    dispatch(loginStart())
    try {
        const res = await axios.post('http://localhost:8000/v1/auth/login', user)  
        console.log('data ', res.data)
        dispatch(loginSuccess(res.data))
        navigate('/')
    } catch (error) {
        dispatch(loginFailed())
    }

}