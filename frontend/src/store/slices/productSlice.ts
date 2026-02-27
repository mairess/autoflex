import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";

import * as productService from "../../service/productService";
import type { ProductResponseType, ProductCreationType } from "../../types/product";
import { getErrorMessage } from "../../utils/getErrorMessage";

interface ProductState {
  items: ProductResponseType[];
  loading: boolean;
  error: string | null;
}

const initialState: ProductState = {
  items: [],
  loading: false,
  error: null,
};

export const fetchProducts = createAsyncThunk<
  ProductResponseType[],
  void,
  { rejectValue: string }
>("products/fetchAll", async (_, { rejectWithValue }) => {
  try {
    return await productService.getAllProducts();
  } catch (error: unknown) {
    return rejectWithValue(getErrorMessage(error));
  }
});

export const createProduct = createAsyncThunk<
  ProductResponseType,
  ProductCreationType,
  { rejectValue: string }
>("products/create", async (data, { rejectWithValue }) => {
  try {
    return await productService.createProduct(data);
  } catch (error: unknown) {
    return rejectWithValue(getErrorMessage(error));
  }
});

export const updateProduct = createAsyncThunk<
  ProductResponseType,
  { id: number; data: ProductCreationType },
  { rejectValue: string }
>("products/update", async ({ id, data }, { rejectWithValue }) => {
  try {
    return await productService.updateProduct(id, data);
  } catch (error: unknown) {
    return rejectWithValue(getErrorMessage(error));
  }
});

export const deleteProduct = createAsyncThunk<
  number,
  number,
  { rejectValue: string }
>("products/delete", async (id, { rejectWithValue }) => {
  try {
    await productService.deleteProduct(id);
    return id;
  } catch (error: unknown) {
    return rejectWithValue(getErrorMessage(error));
  }
});

const productSlice = createSlice({
  name: "products",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchProducts.pending, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchProducts.fulfilled, (state, action) => {
        state.loading = false;
        state.items = action.payload;
      })
      .addCase(fetchProducts.rejected, (state, action) => {
        state.loading = false;
        state.error = action.payload ?? "Unexpected error";
      })
      .addCase(createProduct.fulfilled, (state, action) => {
        state.items.push(action.payload);
      })
      .addCase(updateProduct.fulfilled, (state, action) => {
        const index = state.items.findIndex((item) => item.id === action.payload.id);
        if (index !== -1) state.items[index] = action.payload;
      })
      .addCase(deleteProduct.fulfilled, (state, action) => {
        state.items = state.items.filter((item) => item.id !== action.payload);
      });
  },
});

export default productSlice.reducer;
