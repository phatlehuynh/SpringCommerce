import axios from "axios"
import { getUserStart, getUsersFailed, getUsersSuccess } from "../redux/userSlice"

export const getAllUsers = async (accessToken, dispatch) => {

    dispatch(getUserStart())

    console.log('token ',accessToken)
    try {
        const res = await axios.get('http://localhost:8000/v1/user/', {
            headers: {token: `Bearer ${accessToken}`}
        })
        console.log('res data' ,res.data)
        dispatch(getUsersSuccess(res.data))

    } catch (error) {
        dispatch(getUsersFailed())
        console.log(error)
    }
}