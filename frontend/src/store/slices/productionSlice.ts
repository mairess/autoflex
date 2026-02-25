import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";

import * as productionService from "../../service/productionService";
import type { ProductionSuggestionType } from "../../types/production";
import { getErrorMessage } from "../../utils/getErrorMessage";

interface ProductionState {
  suggestions: ProductionSuggestionType[];
  loading: boolean;
  error: string | null;
}

const initialState: ProductionState = {
  suggestions: [],
  loading: false,
  error: null,
};

export const fetchProductionSuggestion = createAsyncThunk<
  ProductionSuggestionType[],
  void,
  { rejectValue: string }
>("production/fetchSuggestions", async (_, { rejectWithValue }) => {
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
      .addCase(fetchProductionSuggestion.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchProductionSuggestion.fulfilled, (state, action) => {
        state.loading = false;
        state.suggestions = action.payload;
      })
      .addCase(fetchProductionSuggestion.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload ?? "Unexpected error";
      });
  },
});

export default productionSlice.reducer;