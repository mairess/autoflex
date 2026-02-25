import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";

import * as rawMaterialService from "../../service/rawMaterialService";
import type {
  RawMaterialResponseType,
  RawMaterialCreationType,
} from "../../types/rawMaterial";
import { getErrorMessage } from "../../utils/getErrorMessage";

interface RawMaterialState {
  items: RawMaterialResponseType[];
  loading: boolean;
  error: string | null;
}

const initialState: RawMaterialState = {
  items: [],
  loading: false,
  error: null,
};

export const fetchRawMaterials = createAsyncThunk<
  RawMaterialResponseType[],
  void,
  { rejectValue: string }
>("rawMaterials/fetchAll", async (_, { rejectWithValue }) => {
  try {
    return await rawMaterialService.getAllRawMaterials();
  } catch (error: unknown) {
    return rejectWithValue(getErrorMessage(error));
  }
});

export const createRawMaterial = createAsyncThunk<
  RawMaterialResponseType,
  RawMaterialCreationType,
  { rejectValue: string }
>("rawMaterials/create", async (data, { rejectWithValue }) => {
  try {
    return await rawMaterialService.createRawMaterial(data);
  } catch (error: unknown) {
    return rejectWithValue(getErrorMessage(error));
  }
});

export const updateRawMaterial = createAsyncThunk<
  RawMaterialResponseType,
  { id: number; data: RawMaterialCreationType },
  { rejectValue: string }
>("rawMaterials/update", async ({ id, data }, { rejectWithValue }) => {
  try {
    return await rawMaterialService.updateRawMaterial(id, data);
  } catch (error: unknown) {
    return rejectWithValue(getErrorMessage(error));
  }
});

export const deleteRawMaterial = createAsyncThunk<
  number,
  number,
  { rejectValue: string }
>("rawMaterials/delete", async (id, { rejectWithValue }) => {
  try {
    await rawMaterialService.deleteRawMaterial(id);
    return id;
  } catch (error: unknown) {
    return rejectWithValue(getErrorMessage(error));
  }
});

const rawMaterialSlice = createSlice({
  name: "rawMaterials",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchRawMaterials.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchRawMaterials.fulfilled, (state, action) => {
        state.loading = false;
        state.items = action.payload;
      })
      .addCase(fetchRawMaterials.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload ?? "Unexpected error";
      })
      .addCase(createRawMaterial.fulfilled, (state, action) => {
        state.items.push(action.payload);
      })
      .addCase(updateRawMaterial.fulfilled, (state, action) => {
        const index = state.items.findIndex(
          (item) => item.id === action.payload.id,
        );
        if (index !== -1) state.items[index] = action.payload;
      })
      .addCase(deleteRawMaterial.fulfilled, (state, action) => {
        state.items = state.items.filter(
          (item) => item.id !== action.payload,
        );
      });
  },
});

export default rawMaterialSlice.reducer;