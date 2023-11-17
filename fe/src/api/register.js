import axios from 'axios'
import { registerFailed, registerSuccess, registerStart } from '../redux/authSlice'

export const registerUser = async(user, dispatch, navigate) => {
    dispatch(registerStart())
    try {
        await axios.post('http://localhost:8000/v1/auth/register', user)  
        dispatch(registerSuccess())
        navigate('/login')
    } catch (error) {
        dispatch(registerFailed())
    }

}