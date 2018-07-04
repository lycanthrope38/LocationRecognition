# -*- coding: utf-8 -*-

import os
import numpy as np
import copy
import  sys

def get_unique_locations(locations):
    unique_locations = []
    unique_string = []
    location = []
    for loc_part in locations:
        if loc_part[1] == 'B-LOC':
            if len(location) > 0:
                if ' '.join(location) not in unique_string:
                    unique_locations.append(location)
                    unique_string.append(' '.join(location))
                location = []
        location.append(loc_part[0])
    return unique_locations

def generate_replacement_dict(unique_locations, growth_rate):
    result_dict = {}
    for location in unique_locations:
        sample_list = [loc for loc in unique_locations if loc != location]
        result_dict[' '.join(location)] = np.random.choice(sample_list, growth_rate)
    return result_dict


def get_location_from_token_index(tokens, index):
    location = []
    while len(tokens[index]) ==2:
        if tokens[index][1] in ['B-LOC', 'C-LOC']:
            location.append(tokens[index][0])
            index+=1
        else:
            break
    return ' '.join(location)

def  replace_tokens(tokens, rep_index, rep_dict, gr):
    rep_key = get_location_from_token_index(tokens, rep_index)
    replacement = rep_dict[rep_key]
    for i in range(len(rep_key.split())):
        del tokens[rep_index]
    for i  in range(len(replacement[gr])):
        tokens.insert(rep_index + i, [replacement[gr][i], 'C-LOC'])
    tokens[rep_index][1]='B-LOC'
    return tokens

def data_augmentation(tokens, indices, unique_locations, growth_rate = 5):
    assert growth_rate > 1
    assert isinstance(growth_rate, int)
    replacement_dict = generate_replacement_dict(unique_locations, growth_rate)
    result = copy.deepcopy(tokens)
    for gr  in range(growth_rate):
        tokens_to_replace = copy.deepcopy(tokens)
        for i, token in enumerate(tokens_to_replace):
            if len(token)>1:
                if token[1] == 'B-LOC':
                    replace_tokens(tokens_to_replace, i, replacement_dict, gr)

        result.extend(tokens_to_replace)
    return result

def specific_duplicate(tokens, indices, unique_locations, probability):
    pass

def check_argument(mode, message):
    assert mode in ['aug', 'spe'] and (mode=='aug' and len(sys.argv)==3 \
    or mode=='aug' and len(sys.argv)==4), message


def main():
    USAGE_MESSAGE = 'Usage: \n\
        mode augmentation: python data_augmentation.py aug [data path] [growth rate]\n\
                      e.g. python data_augmentation.py aug train.txt 40\n\n\
        mode specific aug: python data_augmentation.py spe [data path] [location] [probability]\n\
                      e.g. python data_augmentation.py spe train.txt \"Hồ Xuân Hương\" 0.3'
    
    if len(sys.argv) < 3:
        raise ValueError(USAGE_MESSAGE)
    mode = sys.argv[1]
    check_argument(mode, USAGE_MESSAGE)

    if mode=='aug':
        file_path = sys.argv[2].replace("\"", "").replace("\'", "")
        growth_rate = int(sys.argv[3])
    if mode=='spe':
        file_path = sys.argv[2].replace("\"", "").replace("\'", "")
        ratio = float(sys.argv[3])
        location = sys.argv[4].replace("\"", "").replace("\'", "")

    current_path = os.path.dirname(os.path.realpath(__file__))
    with open(os.path.join(file_path)) as f:
        tokens = f.read().split('\n')
        for i in range(len(tokens)):
            tokens[i] = tokens[i].split()

    locations = [];    location_indices = []
    for i, token in enumerate(tokens):
        if len(token)==2:
            if(token[1])=='B-LOC':
                locations.append(token)
                location_indices.append(i)
            if(token[1]=='C-LOC'):
                locations.append(token)

    unique_locations = get_unique_locations(locations)
    # if 
    augmented_data = data_augmentation(tokens, location_indices, \
        unique_locations, growth_rate)

    with  open(file_path+'.generated', 'w') as f_write:
        for item in augmented_data:
            print >> f_write, ' '.join(item)
        print('file wrote at: {}'.format(file_path+'.generated'))

if __name__ == "__main__":
    main()