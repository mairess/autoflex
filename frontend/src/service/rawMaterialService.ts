import type { RawMaterialCreationType, RawMaterialResponseType } from "../types/rawMaterial";
import api from "./api";

export const getAllRawMaterials = async () => {
  const response =  await api.get<RawMaterialResponseType[]>("/raw-materials");
  return response.data;
};

export const createRawMaterial = async (data: RawMaterialCreationType) => {
  const response =  await api.post<RawMaterialResponseType>("/raw-materials", data);
  return response.data;
};

export const updateRawMaterial = async (id: number, data: RawMaterialCreationType) => {
  const response =  await api.put<RawMaterialResponseType>(`/raw-materials/${id}`, data);
  return response.data;
};

export const deleteRawMaterial = async (id: number) => {
  await api.delete<void>(`/raw-materials/${id}`);
};
