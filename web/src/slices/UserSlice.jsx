import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  loginToken: '',
  loginId: ''
};

export const UserSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    changeLoginInfo(state, action) {
      state.loginToken = action.payload;
    },
    changeLoginId(state, action) {
      state.loginId = action.payload;
    }
  }
});

export const { changeLoginInfo, changeLoginId } = UserSlice.actions;

export default UserSlice.reducer;
