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
    <div className="border rounded-lg p-4 flex justify-between items-center shadow-sm hover:shadow-md transition">
      <div>
        <h3 className="font-semibold text-gray-800">
          {rawMaterial.name}
        </h3>
        <p className="text-sm text-gray-500">
          Stock: {rawMaterial.stockQuantity}
        </p>
      </div>

      <div className="flex gap-2">
        <button
          onClick={onEdit}
          className="bg-yellow-500 hover:bg-yellow-600 text-white px-3 py-1 rounded-lg text-sm"
        >
          Edit
        </button>

        <button
          onClick={handleDelete}
          className="bg-red-500 hover:bg-red-600 text-white px-3 py-1 rounded-lg text-sm"
        >
          Delete
        </button>
      </div>
    </div>
  );
};

export default RawMaterialItem;