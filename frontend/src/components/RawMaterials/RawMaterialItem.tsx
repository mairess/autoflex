import React from "react";

import { useAppDispatch } from "../../store/hooks";
import { deleteRawMaterial } from "../../store/slices/rawMaterialSlice";
import type { RawMaterialResponseType } from "../../types/rawMaterial";

interface RawMaterialItemProps {
  rawMaterial: RawMaterialResponseType;
  onEdit: () => void;
}

const RawMaterialItem: React.FC<RawMaterialItemProps> = ({
  rawMaterial,
  onEdit,
}) => {
  const dispatch = useAppDispatch();

  const handleDelete = () => {
    const confirmed = confirm(
      `Are you sure you want to delete "${rawMaterial.name}"?`,
    );

    if (!confirmed) return;

    dispatch(deleteRawMaterial(rawMaterial.id));
  };

  return (
    <div className="border rounded-xl p-6 bg-gray-50 shadow-sm hover:shadow-md transition">
      <div className="flex flex-col md:flex-row md:justify-between md:items-center gap-4">
        <div>
          <p className="text-sm text-gray-500">
        Code: {rawMaterial.code}
          </p>

          <h3 className="text-lg font-semibold text-gray-800">
            {rawMaterial.name}
          </h3>

          <p className="text-blue-600 font-bold mt-1">
        Stock: {rawMaterial.stockQuantity}
          </p>
        </div>

        <div className="flex gap-2">
          <button
            onClick={onEdit}
            className="bg-yellow-500 hover:bg-yellow-600 text-white px-4 py-2 rounded-lg transition cursor-pointer"
          >
        Edit
          </button>

          <button
            onClick={handleDelete}
            className="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded-lg transition cursor-pointer"
          >
        Delete
          </button>
        </div>
      </div>
    </div>
  );
};

export default RawMaterialItem;