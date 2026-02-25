
import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";

import type { ProductResponseType, ProductCreationType } from "../../types/product";
import * as productService from "../../service/productService";

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

export const fetchProducts = createAsyncThunk(
  "products/fetchAll",
  async () => {
    return await productService.getAllProducts();
  },
);

export const createProduct = createAsyncThunk(
  "products/create",
  async (data: ProductCreationType) => {
    return await productService.createProduct(data);
  },
);

export const updateProduct = createAsyncThunk(
  "products/update",
  async ({
    id,
    data,
  }: {
    id: number;
    data: ProductCreationType;
  }) => {
    return await productService.updateProduct(id, data);
  },
);

export const deleteProduct = createAsyncThunk(
  "products/delete",
  async (id: number) => {
    await productService.deleteProduct(id);
    return id;
  },
);

const productSlice = createSlice({
  name: "products",
  initialState,
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchProducts.pending, (state) => {
        state.loading = true;
      })
      .addCase(fetchProducts.fulfilled, (state, action) => {
        state.loading = false;
        state.items = action.payload;
      })
      .addCase(fetchProducts.rejected, (state, action) => {
        state.loading = false;
        state.error = action.error.message || "Error loading products";
      })
      .addCase(createProduct.fulfilled, (state, action) => {
        state.items.push(action.payload);
      }).addCase(deleteProduct.fulfilled, (state, action) => {
        state.items = state.items.filter(
          (item) => item.id !== action.payload,
        );
      }).addCase(updateProduct.fulfilled, (state, action) => {
        const index = state.items.findIndex(
          (item) => item.id === action.payload.id,
        );
        if (index !== -1) {
          state.items[index] = action.payload;
        }
      });
  },
});

export default productSlice.reducer;
