export type ProductRawMaterialCreationType = {
  rawMaterialId: number;
  requiredQuantity: number;
};

export type ProductCreationType = {
  code: string;
  name: string;
  price: number;
  rawMaterials: ProductRawMaterialCreationType[];
};

export type ProductRawMaterialResponseType = {
  rawMaterialId: number;
  rawMaterialCode: string;
  rawMaterialName: string;
  requiredQuantity: number;
};

export type ProductResponseType = {
  id: number;
  code: string;
  name: string;
  price: number;
  rawMaterials: ProductRawMaterialResponseType[];
};

export type ProductRawMaterialFormType = {
  rawMaterialId: number;
  requiredQuantity: number;
};