import type { ProductCreationType, ProductResponseType } from '../types/product';
import api from './api';

export const getAllProducts = () => {
  return api.get<ProductResponseType[]>('/products');
};

export const getProductById = (id: number) => {
  return api.get<ProductResponseType>(`/products/${id}`);
};

export const createProduct = (data: ProductCreationType) => {
  return api.post<ProductResponseType>('/products', data);
};

export const updateProduct = (id: number, data: ProductCreationType) => {
  return api.put<ProductResponseType>(`/products/${id}`, data);
};

export const deleteProduct = (id: number) => {
  return api.delete<void>(`/products/${id}`);
};
