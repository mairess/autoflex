import type { ProductCreationType, ProductResponseType } from "../types/product";
import api from "./api";

export const getAllProducts = async () => {
  const response = await api.get<ProductResponseType[]>("/products");
  return response.data;
};

export const createProduct = async (data: ProductCreationType) => {
  const response = await api.post<ProductResponseType>("/products", data);
  return response.data;
};

export const updateProduct = async (id: number, data: ProductCreationType) => {
  const response = await api.put<ProductResponseType>(`/products/${id}`, data);
  return response.data;
};

export const deleteProduct = async (id: number) => {
  await api.delete<void>(`/products/${id}`);
};
