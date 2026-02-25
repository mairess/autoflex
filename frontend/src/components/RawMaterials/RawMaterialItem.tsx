import React from "react";

import { useAppDispatch } from "../../store/hooks";
import { deleteRawMaterial } from "../../store/slices/rawMaterialSlice";
import type { RawMaterialResponseType } from "../../types/rawMaterial";

interface RawMaterialItemProps {
  rawMaterial: RawMaterialResponseType;
}

const RawMaterialItem: React.FC<RawMaterialItemProps> = ({ rawMaterial }) => {
  const dispatch = useAppDispatch();

  const handleDelete = () => {
    dispatch(deleteRawMaterial(rawMaterial.id));
  };

  return (
    <div className="border rounded-lg p-4 flex justify-between items-center shadow-sm hover:shadow-md transition">
      <div>
        <h3 className="font-semibold text-gray-800">
          {rawMaterial.name}
        </h3>
        <p className="text-sm text-gray-500">
        Stock: {rawMaterial.stockQuantity}
        </p>
      </div>

      <button
        onClick={handleDelete}
        className="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded-lg text-sm transition"
      >
      Delete
      </button>
    </div>
  );
};

export default RawMaterialItem;