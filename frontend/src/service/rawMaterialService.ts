import type { RawMaterialCreationType, RawMaterialResponseType } from '../types/rawMaterial';
import api from './api';

export const getAllRawMaterials = () => {
  return api.get<RawMaterialResponseType[]>('/raw-materials');
};

export const getRawMaterialById = (id: number) => {
  return api.get<RawMaterialResponseType>(`/raw-materials/${id}`);
};

export const createRawMaterial = (data: RawMaterialCreationType) => {
  return api.post<RawMaterialResponseType>('/raw-materials', data);
};

export const updateRawMaterial = (id: number, data: RawMaterialCreationType) => {
  return api.put<RawMaterialResponseType>(`/raw-materials/${id}`, data);
};

export const deleteRawMaterial = (id: number) => {
  return api.delete<void>(`/raw-materials/${id}`);
};
