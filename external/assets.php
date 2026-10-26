<?php
const EXTFILES_URL = 'https://dl-lv2.pixelmongo.fr/cobblemongo/assets';
const EXTFILES_FOLDER = __DIR__ . '/assets';

function getFilesRecursively(string $dir, string $base = ''): array {
    $result = [];

    $items = array_diff(scandir($dir), ['.', '..']);
    foreach ($items as $item) {
        $fullPath = $dir . '/' . $item;
        $relativePath = $base . $item;

        if (is_dir($fullPath)) {
            $result = array_merge($result, getFilesRecursively($fullPath, $relativePath . '/'));
        } else {
            $result[] = $relativePath;
        }
    }

    return $result;
}

$extfiles = getFilesRecursively(EXTFILES_FOLDER);

$json = ['extfiles' => []];

foreach ($extfiles as $extfile) {
    $fullPath = EXTFILES_FOLDER . '/' . $extfile;
    $json['extfiles'][] = [
        'path' => $extfile,
		'downloadURL' => EXTFILES_URL . '/' . implode('/', array_map('rawurlencode', explode('/', str_replace('\\', '/', $extfile)))),
        'sha1' => sha1_file($fullPath),
        'size' => filesize($fullPath)
    ];
}

header('Content-Type: application/json');
echo json_encode($json, JSON_PRETTY_PRINT);