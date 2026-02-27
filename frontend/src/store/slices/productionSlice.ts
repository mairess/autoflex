import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";

import * as productionService from "../../service/productionService";
import type { ProductionSuggestionType } from "../../types/production";
import { getErrorMessage } from "../../utils/getErrorMessage";

interface ProductionState {
  items: ProductionSuggestionType[];
  loading: boolean;
  error: string | null;
}

const initialState: ProductionState = {
  items: [],
  loading: false,
  error: null,
};

export const fetchProductionSuggestions = createAsyncThunk<
  ProductionSuggestionType[],
  void,
  { rejectValue: string }
>("production/fetchAll", async (_, { rejectWithValue }) => {
  try {
    return await productionService.getProductionSuggestions();
  } catch (error: unknown) {
    return rejectWithValue(getErrorMessage(error));
  }
});

const productionSlice = createSlice({
  name: "production",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchProductionSuggestions.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchProductionSuggestions.fulfilled, (state, action) => {
        state.loading = false;
        state.items = action.payload;
      })
      .addCase(fetchProductionSuggestions.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload ?? "Unexpected error";
      });
  },
});

export default productionSlice.reducer;
