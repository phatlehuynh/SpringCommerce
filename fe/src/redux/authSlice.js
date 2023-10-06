import {createSlice} from '@reduxjs/toolkit'

export const authSlice = createSlice({

    name: "auth",
    initialState:{
        login:{
            currentUser: null,
            isFetching: false,
            error: false
        },

        register:{
            registerSuccess: false,
            isFetching: false,
            error: false,
        },
    },

    reducers:{

        loginStart: (state) => {
            state.login.isFetching = true

        },

        loginSuccess: (state, action) => {
            state.login.isFetching = false
            state.login.currentUser = action.payload
            state.login.error = false
            
        },

        loginFailed: (state) => {
            state.login.isFetching = false
            state.login.error = true
        },

        registerStart: (state) => {
            state.register.isFetching = true

        },

        registerSuccess: (state) => {
            state.register.isFetching = false
            state.register.success = true
            state.register.error = false
            
        },

        registerFailed: (state) => {
            state.logOut.isFetching = false
            state.logOut.error = true
            state.logOut.success = false
        },

        logOutStart: (state) => {
            state.logOut.isFetching = true

        },

        logOutSuccess: (state) => {
            state.logOut.isFetching = false
            state.logOut.currentUser = null
            state.logOut.error = false
            
        },

        logOutFailed: (state) => {
            state.logOut.isFetching = false
            state.logOut.error = true
        },



    }

})

export const {
    loginStart,
    loginFailed,
    loginSuccess,
    registerStart,
    registerSuccess,
    registerFailed,
    logOutStart,
    logOutSuccess,
    logOutFailed
} = authSlice.actions;

export default authSlice.reducer;