import {create} from 'zustand';

const SORT_OPTIONS = {
      MOST_LIKES: 'most likes',
      NEWEST: 'newest',
      OLDEST: 'oldest'
    };

    const useComponentFilterStore = create((set) => ({
      searchQuery: '',
      types: {
        button: true,
        input: true,
        checkbox: true,
        'radio button': true
      },
      sorting: SORT_OPTIONS.MOST_LIKES,
      filter: {
        query: '',
        types: {
          button: true,
          input: true,
          checkbox: true,
          'radio button': true
        },
        sorting: SORT_OPTIONS.MOST_LIKES
      },
      setSearchQuery: (query) => set({ searchQuery: query }),
      setTypes: (types) => set({ types }),
      setSorting: (sorting) => set({ sorting }),
      setFilter: (filter) => set({ filter }),
      resetFilters: () => set({
        searchQuery: '',
        types: {
          button: true,
          input: true,
          checkbox: true,
          'radio button': true
        },
        sorting: SORT_OPTIONS.MOST_LIKES,
        filter: {
          query: '',
          types: {
            button: true,
            input: true,
            checkbox: true,
            'radio button': true
          },
          sorting: SORT_OPTIONS.MOST_LIKES
        }
      })
    }));

    export default useComponentFilterStore;