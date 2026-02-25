import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";

import * as rawMaterialService from "../../service/rawMaterialService";
import type { RawMaterialResponseType } from "../../types/rawMaterial";

interface State {
  items: RawMaterialResponseType[];
  loading: boolean;
}

const initialState: State = {
  items: [],
  loading: false,
};

export const fetchRawMaterials = createAsyncThunk(
  "rawMaterials/fetchAll",
  rawMaterialService.getAllRawMaterials,
);

export const createRawMaterial = createAsyncThunk(
  "rawMaterials/create",
  rawMaterialService.createRawMaterial,
);

export const deleteRawMaterial = createAsyncThunk(
  "rawMaterials/delete",
  async (id: number) => {
    await rawMaterialService.deleteRawMaterial(id);
    return id;
  },
);

const slice = createSlice({
  name: "rawMaterials",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchRawMaterials.pending, (s) => {
        s.loading = true;
      })
      .addCase(fetchRawMaterials.fulfilled, (s, a) => {
        s.loading = false;
        s.items = a.payload;
      }).addCase(createRawMaterial.fulfilled, (s, a) => {
        s.items.push(a.payload);
      }).addCase(deleteRawMaterial.fulfilled, (state, action) => {
        state.items = state.items.filter(
          (item) => item.id !== action.payload,
        );
      });
  },
});

export default slice.reducer;
