<?php
const MODS_URL = 'https://dl-lv2.pixelmongo.fr/cobblemongo/mods';
const MODS_FOLDER = __DIR__ . '/mods';

$json = [
    'mods' => []
];
$mods = [];
$directory = array_diff(scandir(MODS_FOLDER), ['.', '..']);

foreach ($directory as $file) {
    $mods[] = $file;
}

foreach ($mods as $mod) {
    $json['mods'][] = [
        'name' => $mod,
        'downloadURL' => MODS_URL . '/' . rawurlencode($mod),
        'sha1' => sha1_file(MODS_FOLDER . '/' . $mod),
        'size' => filesize(MODS_FOLDER . '/' . $mod)
    ];
}

header('Content-Type: application/json');
echo json_encode($json);