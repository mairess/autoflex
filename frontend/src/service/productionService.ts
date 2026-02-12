import type { ProductionSuggestionType } from '../types/production';
import api from './api';

export const getProductionSuggestions = () => {
  return api.get<ProductionSuggestionType[]>('/production-suggestions');
};
